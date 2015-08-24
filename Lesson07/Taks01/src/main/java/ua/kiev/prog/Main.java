package ua.kiev.prog;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Main {

    static Group g1 = new Group("Group-1", "My group");
    static Group g2 = new Group("Group-2", "Another group");
    static Client c = null;
    static Address a;
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
    static EntityManager em = emf.createEntityManager();
    static String name;

    public static void main(String[] args) {

        try {

            long cid = -1, gid = -1;


/*            try {
                em.getTransaction().begin();

                // persist groups
                em.persist(g1);
                em.persist(g2);

                System.out.println(gid = g1.getId());

                cid = generate_clients(); // last client id

                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
                ex.printStackTrace();
            }*/

            /*// find group by id
            Group group = em.find(Group.class, gid);
            for (Client cli : group.getClients())
                System.out.println("Name: " + cli.getName() + ", E-mail: " + cli.getEmail() + ", Address: "
                        + cli.getAddress());

            // find client by id
            Client client = em.find(Client.class, cid);
            System.out.println("Name: " + client.getName() + ", Group: " + c.getGroup().getName());

            // modify record
            try {
                em.getTransaction().begin();
                client.setEmail("newaddress@gmail.com");
                client.setPhone("0440987654");
                em.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                em.getTransaction().rollback();
            }

            // delete group with clients
            try {
                group = em.find(Group.class, gid);
                em.getTransaction().begin();
                em.remove(group);
                em.getTransaction().commit();
            } catch (Exception ex){
                ex.printStackTrace();
                em.getTransaction().rollback();
            }

            */

            /*// select all clients
            Query query = em.createQuery("SELECT c FROM Client c", Client.class);
            List<Client> list = (List<Client>) query.getResultList();
            System.out.println("All clients:");
            for (Client cli : list)
                System.out.println("\tName: " + cli.getName() + ", Group: " + cli.getGroup().getName());*/

            /*// select where
            try {
                query = em.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class);
                query.setParameter("email", "petr@mydomain.com");
                Client qc = (Client) query.getSingleResult();
                System.out.println(">>> Name: " + qc.getName() + ", Group: " + qc.getGroup().getName());
            } catch (NoResultException ex) {
                System.out.println(">>> Not found!");
            } catch (NonUniqueResultException ex) {
                System.out.println(">>> Non unique result!");
            }*/

            // generate courses
            /*Course[] course = new Course[] {new Course("Java Start"), new Course("Java Pro")};
            try {
                em.getTransaction().begin();
                em.persist(course[0]);
                em.persist(course[1]);
                for (Client cli : list) {
                    cli.addCourse(course[RND.nextInt(2)]);
                }
                em.getTransaction().commit();
            } catch (Exception ex){
                ex.printStackTrace();
                em.getTransaction().rollback();
            }*/

            //

            try
            {
                Query query = em.createQuery("SELECT c from Client c inner join c.courses crs where crs.name = :coursename and c.address.city=:city");
                query.setParameter("city", "Kyiv");
                query.setParameter("coursename", "Java Pro");
                List<Client> result = (List<Client>) query.getResultList();
                for (Client client : result)
                    System.out.println("Name: " + client.getName() + "\r\nCity: " + client.getAddress());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                em.getTransaction().rollback();
            }

            /*try
            {
                Query query = em.createQuery("SELECT g.name, g.clients.size from Group g");

                List<Object[]> result = query.getResultList();
                for (Object[] o : result)
                {
                    System.out.println("Group name: " + (String) o[0]);
                    System.out.println("Number of clients: " + (int) o[1]);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                em.getTransaction().rollback();
            }*/

            /*try
            {

                Course CookingCourse = new Course("Cooking course");
                Group cookingGroup;
                Client cookingClient;
                em.getTransaction().begin();
                for (int i=0; i<3; i++)
                {
                    cookingGroup = new Group("Курсы поваров" + i, "");

                    for(int j=0; j<5; j++)
                    {
                        final String cookname = randomName();
                        cookingClient = new Client("Повар " + cookname, randomName().toLowerCase()+"@prog.kiev.ua", randomPhone());
                        cookingGroup.addClient(cookingClient);
                        em.persist(cookingClient);
                    }
                    em.persist(cookingGroup);
                }
                em.getTransaction().commit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                em.getTransaction().rollback();
            }*/


            /*// generate menu
            try
            {
                em.getTransaction().begin();
                Menu m;
                for (String dish : Dishes)
                {
                    m = new Menu(dish, RND.nextInt(1000), RND.nextFloat(), (1==RND.nextInt(2)));
                    em.persist(m);
                }
                em.getTransaction().commit();
            }
            catch (Exception ex)
            {
                em.getTransaction().rollback();
                ex.printStackTrace();
            }*/

            // «стоимость от-до»
            try
            {
                Query query = em.createQuery("SELECT m.name, m.price from Menu m where m.price between 300 and 1000");
                List<Object[]> resultList = (List<Object[]>) query.getResultList();
                for (Object[] o : resultList)
                {
                    System.out.println("Название блюда: " + (String)o[0]);
                    System.out.println("Цена блюда: " + (int)o[1]);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            try
            {
                Query query = em.createQuery("SELECT m.name from Menu m where m.discount = TRUE");
                List<String> resultList = query.getResultList();
                for (String s : resultList)
                {
                    System.out.println("Название блюда со скидкой: " + s);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            try
            {
                Query query = em.createQuery("SELECT m.name, m.weight from Menu m order by m.weight DESC");
                List<Object[]> resultList = query.getResultList();
                float totalweight = 0;
                for (Object o[] : resultList)
                {
                    float currentWeight;
                    if ((currentWeight = totalweight + (float)o[1])<=1)
                    {
                        System.out.println("Блюдо: " + o[0] + ", вес: " + o[1]);
                        totalweight = currentWeight;
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }






            /*VipClient vc = new VipClient();
            vc.setPhone("12345678");
            try {
                em.getTransaction().begin();
                em.persist(vc);
                em.getTransaction().commit();
            } catch (Exception ex){
                ex.printStackTrace();
                em.getTransaction().rollback();
            }*/

        } finally {
            em.close();
            emf.close();
        }
    }

    static final String[] NAMES = {"Ivan", "Petr", "Andrey", "Vsevolod", "Dmitriy"};
    static final String[] CITIES = {"Kyiv", "Odessa"};
    static final String[] Dishes = {"Fish", "Beef", "Chicken", "Ice cream", "Cake"};
    static final Random RND = new Random();

    static String randomName() {
        return NAMES[RND.nextInt(NAMES.length)];
    }

    static String randomPhone() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++)
            sb.append(RND.nextInt(10));
        return sb.toString();
    }

    static long generate_clients()
    {
        // generate users
        for (int i = 0; i < 5; i++) {
            a = new Address("UA", CITIES[i%2], RND.nextInt(300));
            name = randomName();
            c = new Client(name, name.toLowerCase() + "@mydomain.com", randomPhone());
            c.setGroup(g1);
            c.setAddress(a);
            em.persist(a);
            em.persist(c);
        }
                /*for (int i = 0; i < 5; i++) {
                    name = randomName();
                    c = new Client(name, name.toLowerCase() + "@mydomain.com", randomPhone());
                    g2.addClient(c);
                    em.persist(c);
                }*/
        return c.getId();
    }
}
