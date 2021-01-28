import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/screens/profile/profile_class.dart';
import 'package:Cloudgrain_teacher_teach/screens/profile/profile_comment.dart';
import 'package:Cloudgrain_teacher_teach/screens/profile/profile_integration.dart';
import 'package:Cloudgrain_teacher_teach/screens/profile/profile_notification.dart';
import 'package:Cloudgrain_teacher_teach/screens/profile/profile_setting.dart';
import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileScreen extends StatefulWidget {
  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  bool _switchValue = false;
  int _currentTabIndex = 0;
  String notificationCount = '0';

  @override
  void dispose() {
    super.dispose();
  }

  @override
  void initState() {
    this._currentTabIndex == 0;
    User.shared().notificationBus.on<String>().listen((data) {
      setState(() {
        notificationCount = data;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        body: Stack(
      children: <Widget>[
        Padding(
          padding: EdgeInsets.only(left: 232.w),
          child: _tabPage(this._currentTabIndex),
        ),
        Padding(
          padding: EdgeInsets.only(left: 0.w, top: 0.w),
          child: Container(
            width: 232.w,
            height: 768.h,
            decoration: BoxDecoration(
                color: Color.fromRGBO(255, 255, 255, 1),
                boxShadow: [
                  BoxShadow(
                      color: Color.fromRGBO(195, 195, 195, 0.5),
                      offset: Offset(0, 2.w), //阴影xy轴偏移量
                      blurRadius: 7.w, //阴影模糊程度
                      spreadRadius: 0 //阴影扩散程度
                      )
                ]),
            child: Stack(
              children: <Widget>[
                //个人信息
                Padding(
                  padding: EdgeInsets.only(left: 22.w, top: 64.w),
                  child: Container(
                    width: 44.w,
                    height: 44.w,
                    decoration: BoxDecoration(
                        color: Color.fromRGBO(230, 244, 255, 1),
                        borderRadius: BorderRadius.circular(22.w)),
                    child: Image.asset(
                      'assets/avatars/my__avatar_a_default@3x.png',
                      width: 44.w,
                      height: 44.w,
                    ),
                  ),
                ),
                Padding(
                    padding: EdgeInsets.only(left: 74.w, top: 69.w),
                    child: Row(
                      children: <Widget>[
                        Text(
                          User.shared().actualName,
                          style: TextStyle(
                              color: Color.fromRGBO(15, 32, 67, 1),
                              fontSize: 14.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                        Text(
                          '老师',
                          style: TextStyle(
                              color: Color.fromRGBO(15, 32, 67, 1),
                              fontSize: 14.sp,
                              fontFamily: 'PingFangSC-Regular'),
                        ),
                      ],
                    )),
                Padding(
                  padding: EdgeInsets.only(left: 74.w, top: 88.w),
                  child: Text(
                    User.shared().remarks,
                    style: TextStyle(
                        color: Color.fromRGBO(15, 32, 67, 1),
                        fontSize: 10.sp,
                        fontFamily: 'PingFangSC-Regular'),
                  ),
                ),
                //我的班级
                Padding(
                  padding: EdgeInsets.only(top: 133.w),
                  child: _setLabelsWithPath(
                      'assets/icons/my_icon_a@3x.png', '我的班级', 0),
                ),
                Padding(
                  padding: EdgeInsets.only(top: 133.w),
                  child: _setLabelsWithPath(
                      'assets/icons/my_icon_a@3x.png', '我的班级', 0),
                ),
                //我的通知
                Padding(
                  padding: EdgeInsets.only(top: 187.w),
                  child: _setLabelsWithPath(
                      'assets/icons/my_icon_b@3x.png', '我的通知', 1),
                ),
                Padding(
                  padding: EdgeInsets.only(left: 130.w, top: 200.w),
                  child: _buildCount(notificationCount),
                ),
                //我的积分
                Padding(
                  padding: EdgeInsets.only(top: 241.w),
                  child: _setLabelsWithPath(
                      'assets/icons/my_icon_c@3x.png', '我的积分', 2),
                ),
                //设置
                Padding(
                  padding: EdgeInsets.only(top: 295.w),
                  child: _setLabelsWithPath(
                      'assets/icons/my_icon_d@3x.png', '设置', 3),
                ),
                //消息通知
                Padding(
                  padding: EdgeInsets.only(top: 349.w),
                  child: //消息通知
                      Container(
                    width: 232.w,
                    height: 54.w,
                    color: Color.fromRGBO(255, 255, 255, 1),
                    child: Stack(
                      children: <Widget>[
                        Padding(
                          padding: EdgeInsets.only(left: 22.w, top: 14.w),
                          child: Image.asset(
                            'assets/icons/my_icon_g@3x.png',
                            width: 26.w,
                            height: 26.w,
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.only(left: 60.w, top: 17.w),
                          child: Text(
                            '消息通知',
                            style: TextStyle(
                                color: Color.fromRGBO(15, 32, 67, 1),
                                fontSize: 14.sp,
                                fontFamily: 'PingFangSC-Regular'),
                          ),
                        ),
                        Padding(
                            padding: EdgeInsets.only(left: 182.w, top: 17.w),
                            child: Container(
                              width: 34.w,
                              height: 20.w,
                              padding: EdgeInsets.only(right: 19.w),
                              child: CupertinoSwitch(
                                  value: _switchValue,
                                  onChanged: (bool value) {
                                    setState(() {
                                      _switchValue = value;
                                    });
                                  }),
                            ))
                      ],
                    ),
                  ),
                ),

                //学生评语
                Padding(
                  padding: EdgeInsets.only(top: 403.w),
                  child: _setLabelsWithPath(
                      'assets/icons/my_icon_f@3x.png', '老师评语', 4),
                ),
              ],
            ),
          ),
        )
      ],
    ));
  }

  Widget _buildCount(String count) {
    if (count == '0') {
      return Container(
        width: 0,
        height: 0,
      );
    } else {
      return Padding(
        padding: EdgeInsets.only(left: 30.w, top: 3.w),
        child: Container(
          width: 20.w,
          height: 20.w,
          decoration: BoxDecoration(
            color: Colors.red,
            borderRadius: BorderRadius.circular(10.w),
          ),
          child: Center(
            child: Text(
              count,
              style: TextStyle(
                color: Colors.white,
                fontSize: 12.sp,
              ),
            ),
          ),
        ),
      );
    }
  }

  Widget _setLabelsWithPath(String path, String title, int count) {
    return InkWell(
      onTap: () {
        setState(() {
          this._currentTabIndex = count;
        });
      },
      child: Container(
          width: 232.w,
          height: 54.w,
          color: this._currentTabIndex == count
              ? Color.fromRGBO(246, 246, 246, 1)
              : Colors.white,
          //color: Color.fromRGBO(255, 255, 255, 1),
          child: Stack(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.only(left: 22.w, top: 14.w),
                child: Image.asset(
                  path,
                  width: 26.w,
                  height: 26.w,
                ),
              ),
              Padding(
                padding: EdgeInsets.only(left: 60.w, top: 17.w),
                child: Text(
                  title,
                  style: TextStyle(
                      color: Color.fromRGBO(15, 32, 67, 1),
                      fontSize: 14.sp,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(left: 200.w, top: 22.w),
                child: Image.asset(
                  'assets/icons/my_icon_e@3x.png',
                  width: 10.w,
                  height: 10.w,
                ),
              ),
            ],
          )),
    );
  }

  Widget _tabPage(int index) {
    switch (index) {
      case 0:
        return ProfileClassScreen();
        break;
      case 1:
        return ProfileNotificationScreen();
        break;
      case 2:
        return ProfileIntegrationScreen();
        break;
      case 3:
        return ProfileSettingScreen();
        break;
      case 4:
        return ProfileCommentScreen();
        break;
      default:
    }
  }
}
