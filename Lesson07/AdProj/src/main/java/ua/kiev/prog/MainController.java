package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/SpringMVC_war_exploded")
public class MainController {

    @Autowired
    private AdvDAO advDAO;

    @RequestMapping("/")
    public ModelAndView listAdvs() {
        return new ModelAndView("index", "advs", advDAO.list());
    }

    @RequestMapping(value = "/add_page", method = RequestMethod.POST)
    public String addPage(Model model) {
        return "add_page";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(@RequestParam(value = "pattern") String pattern) {
        return new ModelAndView("index", "advs", advDAO.list(pattern));
    }

    // move to Recycled Bin
    @RequestMapping(value = "/recycle", method = RequestMethod.GET)
    public ModelAndView recycle(@RequestParam(value = "id") long id) {
        advDAO.moveToRecycled(id);
        return new ModelAndView("index", "advs", advDAO.list());
    }

    // view the Recycled Bin
    @RequestMapping(value = "/recycled", method = RequestMethod.GET)
    public ModelAndView recycled() {
        return new ModelAndView("recycled", "advs", advDAO.listRecycled());
    }

    // restore from the Recycled Bin
    @RequestMapping(value = "/restore", method = RequestMethod.GET)
    public ModelAndView restore(@RequestParam("id") long id) {
        advDAO.restoreFromRecycled(id);
        return new ModelAndView("recycled", "advs", advDAO.listRecycled());
    }

    // bulk move to the Recycled Bib
    @RequestMapping(value = "/bulkRecycle", method = RequestMethod.POST)
    public ModelAndView bulkRecycle(HttpServletRequest request) {
        final String[] checkboxes = request.getParameterValues("recycleCheckBox");
        List<Long> ids = new ArrayList<>();
        if (null != checkboxes) {
            for (String s : checkboxes) {
                ids.add(Long.parseLong(s));
            }
            advDAO.moveToRecycled(ids);
        }
        return new ModelAndView("index", "advs", advDAO.list());
    }

    // bulk restore from the Recycled Bib
    @RequestMapping(value = "/bulkRestore", method = RequestMethod.POST)
    public ModelAndView bulkRestore(HttpServletRequest request) {
        final String[] checkboxes = request.getParameterValues("restoreCheckBox");
        List<Long> ids = new ArrayList<>();
        if (null != checkboxes) {
            for (String s : checkboxes) {
                ids.add(Long.parseLong(s));
            }
            advDAO.restoreFromRecycled(ids);
        }
        return new ModelAndView("recycled", "advs", advDAO.listRecycled());
    }

	/*@RequestMapping("/delete")
    public ModelAndView delete(@RequestParam(value="id") long id) {
		advDAO.delete(id);
		return new ModelAndView("index", "advs", advDAO.list());
	}*/

    @RequestMapping(value = "/emptyRecycled", method = RequestMethod.GET)
    public ModelAndView deleteForever() {
        advDAO.emptyRecycled();
        return new ModelAndView("recycled", "advs", advDAO.listRecycled());
    }

    @RequestMapping("/image/{file_id}")
    public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable("file_id") long fileId) {
        try {
            byte[] content = advDAO.getPhoto(fileId);
            response.setContentType("image/png");
            response.getOutputStream().write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping(value = "/addXML", method = RequestMethod.POST)
    public ModelAndView addXML(@RequestParam(value = "xmlFile") MultipartFile xmlFile, HttpServletRequest request) {
        if (null==xmlFile || xmlFile.isEmpty())
            return null;
        else
        {
            try {
                Advertisements advertisements = new Advertisements();
                JAXBContext jaxbContext = JAXBContext.newInstance(advertisements.getClass());
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                advertisements = (Advertisements) unmarshaller.unmarshal(xmlFile.getInputStream());
                for (Advertisement adv : advertisements.getAdvertisements()) {
                    advDAO.add(adv);
                }
                return new ModelAndView("index", "advs", advDAO.list());
            } catch (IOException | JAXBException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    @RequestMapping(value = "/getXML", method = RequestMethod.GET)
    public ModelAndView addXML(HttpServletResponse response) {
        Advertisements advertisements = new Advertisements();
        advertisements.setAdvertisements(advDAO.listAll());
        response.setContentType("application/xml;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=\"ads.xml\"");

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(advertisements.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(advertisements, response.getWriter());

            return null;
        }
        catch (JAXBException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addAdv(@RequestParam(value = "name") String name,
                               @RequestParam(value = "shortDesc") String shortDesc,
                               @RequestParam(value = "longDesc", required = false) String longDesc,
                               @RequestParam(value = "phone") String phone,
                               @RequestParam(value = "price") double price,
                               @RequestParam(value = "photo") MultipartFile photo,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            Advertisement adv = new Advertisement(
                    name, shortDesc, longDesc, phone, price,
                    photo.isEmpty() ? null : new Photo(photo.getOriginalFilename(), photo.getBytes())
            );
            advDAO.add(adv);
            return new ModelAndView("index", "advs", advDAO.list());
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }
}