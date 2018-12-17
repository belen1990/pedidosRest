package com.ricardo.persistence;

import java.io.Serializable;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.ricardo.models.Pedido;

public class PedidosManager {

	private static PedidosManager instance = null;

	private static SessionFactory sfactory;

	public static PedidosManager getInstance() {
		if (instance == null) instance = new PedidosManager();
		return instance;
	}

	private PedidosManager() {
		sfactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public List<Pedido> getPedidos() {
		Session sess = sfactory.openSession();

		List<Pedido> listaPedidos = sess.createQuery("from Pedido").list();

		sess.close();
		return listaPedidos;
	}
	
	public int borrarPedido (int pid) throws Exception {
		Session session = sfactory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
	    
	        Object persistentInstance = session.load(Pedido.class, (Serializable) pid);
	        if (persistentInstance != null)  {
	            session.delete(persistentInstance);
	            session.getTransaction().commit();
	            System.out.println ("Deleted Sucessfully"); 

	        }
	        else {
	            System.out.println ("Did not find the  Stock Object in persistance");

	        }

	    } 
	    catch (HibernateException e) {
	        e.printStackTrace();

	    }

	    finally {
	        if(session!=null){
	            session.close();
	        }
	    }

		

		t.commit();
		session.close();
		return pid;

	
	}

}
