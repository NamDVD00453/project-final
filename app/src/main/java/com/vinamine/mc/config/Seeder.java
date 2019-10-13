//package com.vinamine.mc.config;
//
//import com.vinamine.mc.entity.Account_User;
//import com.vinamine.mc.entity.Voucher;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Seeder {
//    public static Account_User successUser = new Account_User();
//
//    public static List<Voucher> listVoucher(){
//        List<Voucher> vouchers = new ArrayList<>();
//        Voucher voucher = new Voucher();
//        voucher.setId(System.currentTimeMillis());
//        voucher.setCode_sale("BLACKFRIDAY");
//        voucher.setCreated(System.currentTimeMillis());
//        voucher.setDescription("Thời điểm hàng loạt thương hiệu trà sữa ra đời thì Pozaa Tea nổi lên như một hiện tượng, trở thành món đồ uống nhận sự yêu thích của rất nhiều người nhờ vào công thức phê chế riêng biệt, hương vị độc đáo, thiết kế mới lạ.");
//        voucher.setExpired(System.currentTimeMillis()+36000000);
//        voucher.setImage("https://img.jamja.vn/jamja-prod/a242125.jpg");
//        voucher.setName("Pozaa Tea");
//        voucher.setPercent(40);
//        voucher.setStatus(1);
//        voucher.setMax_slot(10);
//
//        vouchers.add(voucher);
//        voucher.setName("Pozaa Tea 2");
//        vouchers.add(voucher);
//        voucher.setName("Pozaa Tea 3");
//        vouchers.add(voucher);
//        voucher.setName("Pozaa Tea 4");
//        vouchers.add(voucher);
//        voucher.setName("Pozaa Tea 5");
//        vouchers.add(voucher);
//        voucher.setName("Pozaa Tea 6");
//        vouchers.add(voucher);
//        voucher.setName("Pozaa Tea 7");
//        vouchers.add(voucher);
//
//        return vouchers;
//    }
//
//
//}
