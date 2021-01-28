import 'dart:convert';

import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:Cloudgrain_teacher_teach/widgets/profile_notification_cell.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileNotificationScreen extends StatefulWidget {
  @override
  _ProfileNotificationScreenState createState() =>
      _ProfileNotificationScreenState();
}

class _ProfileNotificationScreenState extends State<ProfileNotificationScreen> {
  dynamic notificationList = '';
  @override
  void initState() {
    super.initState();
    _refresh();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          centerTitle: true,
          backgroundColor: Colors.white,
          brightness: Brightness.light,
          elevation: 0.8,
          title: Text(
            '我的通知',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: _buildClassScrollView(),
        ));
  }

  CustomScrollView _buildClassScrollView() {
    if (notificationList == '') {
      return CustomScrollView(
        slivers: <Widget>[
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          _buildNotificationList(),
        ],
      );
    }
  }

  SliverToBoxAdapter _buildEmptyView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 722.w,
        height: 696.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 212.w, top: 205.w),
              child: Image.asset(
                'assets/images/yk_illustration_a@3x.png',
                width: 96.w,
                height: 96.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 227.w),
              child: Text(
                '您当前没有通知',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 256.w),
              child: Text(
                '休息一下吧～',
                style: TextStyle(
                    color: Color.fromRGBO(155, 157, 161, 1),
                    fontSize: 12.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  SliverFixedExtentList _buildNotificationList() {
    return SliverFixedExtentList(
      itemExtent: 206.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
            alignment: Alignment.center,
            child: InkWell(
              onTap: notificationList[index]['bizStatus'].toString() == '0'
                  ? () {
                      showCupertinoDialog(
                          context: context,
                          builder: (context) {
                            return CupertinoAlertDialog(
                              title: Text('允许该生加入班级'),
                              actions: [
                                CupertinoDialogAction(
                                  child: Text('同意'),
                                  onPressed: () {
                                    setDataOfAllowMemberIn(index, true);
                                    Navigator.of(context).pop();
                                    //
                                  },
                                ),
                                CupertinoDialogAction(
                                  child: Text('拒绝'),
                                  isDestructiveAction: true,
                                  onPressed: () {
                                    setDataOfAllowMemberIn(index, false);
                                    Navigator.of(context).pop();
                                    //
                                  },
                                ),
                              ],
                            );
                          });
                    }
                  : null,
              child: new Container(
                //width: 654.w,
                height: 206.w,
                padding: EdgeInsets.fromLTRB(76.w, 18.w, 76.w, 18.w),
                color: Colors.white,
                child: ProfileNotificationCell(
                  title: notificationList[index]['title'],
                  message: notificationList[index]['message'],
                  data: notificationList[index]['createDate'],
                  classId:
                      jsonDecode(notificationList[0]['customData'].toString())[
                          'studentId'],
                  studentId: jsonDecode(
                      notificationList[0]['customData'].toString())['classId'],
                  biz: notificationList[index]['bizStatus'].toString(),
                ),
              ),
            ));
      }, childCount: notificationList.length),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfClassList();
      return null;
    });
  }

  void setDataOfClassList() {
    //网络请求
    FormData params = FormData.fromMap({
      'pageNumber': 1,
      'pageSize': 8,
      "firstType": "class",
      "secondType": "studentJoin",
    });

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/pushMessage/list", params, (result) {
      setState(() {
        this.notificationList = result['data']['records'];
        int count = 0;
        for (int i = 0; i < notificationList.length; i++) {
          if (notificationList[i]['readStatus'].toString() == '1') {
            count++;
          }
        }
        User.shared().eventBus.fire(count.toString());
      });
      //验证通过提交数据
    }, (error) {});
    //
  }

  void setDataOfAllowMemberIn(int index, bool isAllow) {
    //网络请求
    FormData params = FormData.fromMap({
      'messageId': notificationList[index]['id'].toString(),
      'classId': jsonDecode(
          notificationList[index]['customData'].toString())['classId'],
      'studentId': jsonDecode(
          notificationList[index]['customData'].toString())['studentId'],
      'reason': isAllow ? "已审核通过" : "已驳回请求",
      "reject": isAllow ? "0" : "1",
    });

    // print();

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/class/member/approve", params, (result) {
      print(result);
      setState(() {
        notificationList[index]['bizStatus'] = isAllow ? '1' : '2';
      });
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('处理成功'),
            content: Text('\n审核处理成功'),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );

      //验证通过提交数据
    }, (error) {
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('处理失败'),
            content: Text(error),
            actions: <Widget>[
              CupertinoDialogAction(
                child: Text('确认'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    });
    //
  }
}
