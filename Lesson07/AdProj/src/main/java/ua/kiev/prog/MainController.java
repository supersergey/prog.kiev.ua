package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
	public ModelAndView search(@RequestParam(value="pattern") String pattern) {
		return new ModelAndView("index", "advs", advDAO.list(pattern));
	}

	// move to Recycled Bin
    @RequestMapping(value ="/recycle", method = RequestMethod.GET)
	public ModelAndView recycle(@RequestParam(value="id") long id)
	{
		advDAO.moveToRecycled(id);
        return new ModelAndView("index", "advs", advDAO.list());
	}

    // view the Recycled Bin
    @RequestMapping(value="/recycled", method = RequestMethod.GET)
    public ModelAndView recycled()
    {
        return new ModelAndView("recycled", "advs", advDAO.listRecycled());
    }

    // restore from the Recycled Bin
    @RequestMapping(value="/restore", method = RequestMethod.GET)
    public ModelAndView restore(@RequestParam("id") long id)
    {
        advDAO.restoreFromRecycled(id);
        return new ModelAndView("recycled", "advs", advDAO.listRecycled());
    }

    // bulk move to the Recycled Bib
    @RequestMapping(value="/bulkRecycle", method = RequestMethod.POST)
    public ModelAndView bulkRecycle(HttpServletRequest request)
    {
        final String[] checkboxes = request.getParameterValues("recycleCheckBox");
        List<Long> ids = new ArrayList<>();
        if (null!=checkboxes) {
            for (String s : checkboxes)
            {
                ids.add(Long.parseLong(s));
            }
            advDAO.moveToRecycled(ids);
        }
        return new ModelAndView("index", "advs", advDAO.list());
    }

    // bulk restore from the Recycled Bib
    @RequestMapping(value="/bulkRestore", method = RequestMethod.POST)
    public ModelAndView bulkRestore(HttpServletRequest request)
    {
        final String[] checkboxes = request.getParameterValues("restoreCheckBox");
        List<Long> ids = new ArrayList<>();
        if (null!=checkboxes) {
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

    @RequestMapping(value = "/add_XML", method = RequestMethod.POST)
    public void addXML(@RequestParam(value="name") String name, @RequestParam(value="xml") MultipartFile xmlfile, HttpRequest request)
    {

    }

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addAdv(@RequestParam(value="name") String name,
						 @RequestParam(value="shortDesc") String shortDesc,
						 @RequestParam(value="longDesc", required=false) String longDesc,
						 @RequestParam(value="phone") String phone,
						 @RequestParam(value="price") double price,
						 @RequestParam(value="photo") MultipartFile photo,
						 HttpServletRequest request,
						 HttpServletResponse response)
	{
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