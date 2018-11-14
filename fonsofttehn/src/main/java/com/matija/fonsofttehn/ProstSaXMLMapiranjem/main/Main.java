package com.matija.fonsofttehn.ProstSaXMLMapiranjem.main;

import java.util.Iterator;
import java.util.List;

import com.matija.fonsofttehn.ProstSaXMLMapiranjem.model.Proizvod;
import com.matija.fonsofttehn.ProstSaXMLMapiranjem.util.HibernateUtility;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

public class Main {

	/**
	 * @param args
	 */
    public static void main(String[] args) {

        Main m=new Main();
        m.kreirajIUbaciProizvod(55,"Brasno",40);  
        List l=m.listProizvod();
        Iterator i=l.iterator();
        while(i.hasNext()){
            Object p=i.next();
            System.out.println(p);
        }
        /*Proizvod p=new Proizvod(44,"aaaaaaaa",50);
        m.obrisiProizvod(p);
        System.out.println(m.procitajProizvod(44));
        m.criteriaProizvod();
        m.listProizvodSQL();
        System.out.println("qbe: "+m.loadByExample());*/
        HibernateUtility.getSessionFactory().close();
    }
    
    public void kreirajIUbaciProizvod(int id, String naziv, double cena){
        Session session=HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        Proizvod p=new Proizvod();
        p.setProizvodID(id);
        p.setNaziv(naziv);
        p.setCena(cena);
        session.saveOrUpdate(p);
        session.getTransaction().commit(); 
    }
    
    public List listProizvod(){
        Session session=HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        Query query=session.createQuery("from Proizvod");
        List lista=query.list();
        session.getTransaction().commit();
        return lista;
    }
    
    public List criteriaProizvod(){
        Session sesija=HibernateUtility.getSessionFactory().openSession();
        sesija.beginTransaction();
        Criteria crit = sesija.createCriteria(Proizvod.class);
        //postavlja maksimalni broj procitanih slogova
        crit.setMaxResults(3);
        //uvodi kriterijum da naziv proiyvoda treba da pocinje slovom 'p'
        crit.add(Restrictions.like("naziv", "p%"));
        List lista = crit.list();
        sesija.getTransaction().commit();
        System.out.println(lista);
        return lista;
    }
    
    public Proizvod loadByExample() {
    	Session sesija=HibernateUtility.getSessionFactory().openSession();
    	sesija.beginTransaction();
    	Proizvod p = new Proizvod();
    	p.setNaziv("Cokoladna Bananica");
    	p.setCena(70);
    	Criteria crit = sesija.createCriteria(Proizvod.class);
    	crit.add(Example.create(p));
    	p = (Proizvod) crit.uniqueResult();
    	sesija.getTransaction().commit();
    	return p;
   }

    
    public Proizvod procitajProizvod(int i){
        Session session=HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        Proizvod p=(Proizvod)session.get(Proizvod.class, i);//ili load, ali baca Exception ako ne nadje
        System.out.println("Proizvod preko get: "+p);
        session.getTransaction().commit();
        return p;
    }
    
    public void azurirajProizvod(Proizvod p){
        Session session=HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(p);
        session.getTransaction().commit();
    }
    
    public void obrisiProizvod(Proizvod p){
        Session session=HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(p);
        session.getTransaction().commit();
    }
    public List listProizvodSQL(){
        Session session=HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "select {proizvod.*} from Proizvod proizvod";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity("proizvod", Proizvod.class);
        List lista = query.list();
        session.getTransaction().commit();
        return lista;
    }
}
