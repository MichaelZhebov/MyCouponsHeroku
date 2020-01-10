package com.main.app;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.main.beans.Coupon;

@Component
public class CouponExpirationDayliJob implements Runnable {

//	private CouponDAO couponsDAO = new CouponDAO();
//	private boolean quit = false;
//	private Date lastCleanUp = null;
//	private static CouponExpirationDayliJob mJob;
//	private static Thread mainThread;
//	
//
//	public CouponExpirationDayliJob() {
//	}
//
//	private void cleanUp() {
//		lastCleanUp = new Date();
//		ArrayList<Coupon> coupons = (ArrayList<Coupon>) couponsDAO.getAllCoupons(); 
//		for (Coupon c : coupons) {
//			if (c.getEndDate().before(new Date())) {
//				//couponsDAO.deleteAllCouponPurchase(c.getId()); else don't know how to do
//				couponsDAO.deleteCoupon(c);
//			}
//		}
//
//	}

	@Override
	public void run() {
//		Date now = new Date();
		//cleanUp();
//		while (!quit) {
//			if (now.getTime() - lastCleanUp.getTime() >= 86400000) {
//			cleanUp();
//			}
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void stop() {
		//quit = true;
	}
	
	public static void startBackgroundJobs() {
//		mJob = new CouponExpirationDayliJob();
//		mainThread = new Thread(mJob);
//		mainThread.start();
	}
	
	public static void stopBackgroundJobs() {
//		mJob.stop();
//		try {
//			mainThread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	

}
