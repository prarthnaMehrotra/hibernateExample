package com.cl.hibernatewithannotations.entity;

import java.util.List;
import java.util.Scanner;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentMenu {

	Transaction transaction = null;
	Session session = null;

	StudentInfo si = new StudentInfo();
	Scanner sc = new Scanner(System.in);
	char ch = 'y';

	public void menu() {
		while (ch == 'y') {
			System.out.println("Student details : ");
			listStudentDetails();
			System.out.println("Choose from the below operations : ");
			System.out.println("insert : insert student details");
			System.out.println("delete : delete student details");
			System.out.println("update : update student details");
			String choice = sc.next();

			switch (choice) {
			case "insert":
				insertIntoStudentInfo();
				break;
			case "delete":
				deleteFromStudentInfo();
				break;
			case "update":
				updateStudentInfo();
				break;
			default:
				break;
			}
			System.out.println("Do you want to continue? y/n : ");
			ch = sc.next().trim().charAt(0);
		}
	}

	private void updateStudentInfo() {
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = session.beginTransaction();
			String hql = "update StudentInfo set studentEmail = :email where id = :id";
			Query<?> query = session.createQuery(hql);
			System.out.println("Enter the student id : ");
			query.setParameter("id", sc.nextInt());
			System.out.println("Enter the student email : ");
			query.setParameter("email", sc.next());

			int rowCount = query.executeUpdate();
			System.out.println("Rows affected: " + rowCount);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listStudentDetails() {
		try (Session session1 = HibernateUtil.getSessionFactory().openSession()) {
			List<StudentInfo> studentList = session1.createQuery("from StudentInfo", StudentInfo.class).list();
			studentList.stream().forEach(System.out::println);
		}
	}

	private void deleteFromStudentInfo() {

		try (Session session1 = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = session1.beginTransaction();
			System.out.println("Enter the student id : ");
			int id = sc.nextInt();
			Query<?> deleteQuery1 = session1.createQuery("delete from StudentInfo where studentId = :id");
			deleteQuery1.setParameter("id", id);
			int rowCount = deleteQuery1.executeUpdate();
			if (rowCount != 0) {
				System.out.println("rows deleted = " + rowCount);
			} else {
				System.out.println("no rows found");
			}
			tx.commit();
		}
	}

	private void insertIntoStudentInfo() {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			StudentInfo student = new StudentInfo();
			System.out.println("Enter student name : ");
			student.setStudentName(sc.next());
			System.out.println("Enter the student email : ");
			student.setStudentEmail(sc.next());
			transaction = session.beginTransaction();
			session.save(student);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}

	// for deleting the student details
	// try (Session session1 = HibernateUtil.getSessionFactory().openSession()) {
	// Transaction transaction1 = session1.beginTransaction();
	// Query deleteQuery = session1.createQuery("delete from student;");
	// int rowCount = deleteQuery.executeUpdate();
	// if (rowCount != 0) {
	// System.out.println("rows deleted = " + rowCount);
	// } else {
	// System.out.println("no rows found");
	// }
	// transaction1.commit();
	// }

}
