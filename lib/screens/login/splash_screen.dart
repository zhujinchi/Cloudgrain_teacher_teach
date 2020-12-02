import 'package:Cloudgrain_teacher_teach/screens/login/login_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import '../screens.dart';
import 'dart:async';

class SplashPage extends StatefulWidget {
  SplashPage({Key key}) : super(key: key);
  @override
  _SplashPage createState() => new _SplashPage();
}

class _SplashPage extends State<SplashPage> {
  bool isStartHomePage = false;

  @override
  void initState() {
    super.initState();
    //开启倒计时
    countDown();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return new GestureDetector(
      onTap: goToLoginPage, //设置页面点击事件
      // child: Image.asset(
      //   "images/splash_img.jpg",
      //   fit: BoxFit.cover,
      // ),
      child: Container(
        width: 375.w,
        height: 667.h,
        color: Color.fromRGBO(235, 236, 240, 1),
        child: Center(
          child: Image.asset(
            'assets/icons/registered_icon_a@3x.png',
            width: 130.w,
            height: 130.w,
          ),
        ),
      ),
    );
  }

  void countDown() {
    //设置倒计时三秒后执行跳转方法
    var duration = new Duration(seconds: 3);
    new Future.delayed(duration, goToLoginPage);
  }

  void goToLoginPage() {
    //如果页面还未跳转过则跳转页面
    if (!isStartHomePage) {
      //跳转主页 且销毁当前页面
      Navigator.of(context).pushAndRemoveUntil(
          new MaterialPageRoute(builder: (context) => new LoginScreen()),
          (Route<dynamic> rout) => false);
      isStartHomePage = true;
    }
  }
}
