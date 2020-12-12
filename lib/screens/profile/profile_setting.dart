import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileSettingScreen extends StatefulWidget {
  @override
  _ProfileSettingScreenState createState() => _ProfileSettingScreenState();
}

class _ProfileSettingScreenState extends State<ProfileSettingScreen> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Color.fromRGBO(246, 246, 246, 1),
        appBar: AppBar(
          centerTitle: true,
          backgroundColor: Colors.white,
          brightness: Brightness.light,
          elevation: 0.8,
          title: Text(
            '设置',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: CustomScrollView(
            slivers: <Widget>[
              _buildView(),
            ],
          ),
        ));
  }

  SliverToBoxAdapter _buildView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 722.w,
        height: 696.w,
        color: Color.fromRGBO(246, 246, 246, 1),
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 34.w, top: 38.w),
              child: Container(
                width: 654.w,
                height: 50.w,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(5.w),
                ),
                child: Stack(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.only(left: 20.w, top: 13.w),
                      child: Text(
                        '清除缓存',
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 18.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 570.w, top: 12.w),
                      child: Text(
                        '0.9M',
                        style: TextStyle(
                            color: Color.fromRGBO(155, 157, 161, 1),
                            fontSize: 18.sp,
                            letterSpacing: -0.2.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 625.w, top: 17.w),
                      child: Image.asset(
                        'assets/icons/my_dy_icon_a@3x.png',
                        width: 9.w,
                        height: 16.w,
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 34.w, top: 106.w),
              child: Container(
                width: 654.w,
                height: 50.w,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(5.w),
                ),
                child: Stack(
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.only(left: 20.w, top: 13.w),
                      child: Text(
                        '关于我们',
                        style: TextStyle(
                            color: Color.fromRGBO(15, 32, 67, 1),
                            fontSize: 18.sp,
                            fontFamily: 'PingFangSC-Regular'),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(left: 625.w, top: 17.w),
                      child: Image.asset(
                        'assets/icons/my_dy_icon_a@3x.png',
                        width: 9.w,
                        height: 16.w,
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 34.w, top: 324.w),
              child: Container(
                width: 654.w,
                height: 44.w,
                decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(6.w)),
                child: OutlineButton(
                  borderSide: BorderSide(color: Color.fromRGBO(0, 145, 255, 1)),
                  child: Text(
                    '退出登录',
                    style: TextStyle(
                        fontSize: 22.sp,
                        fontFamily: 'PingFangSC-Regular',
                        color: Color.fromRGBO(0, 145, 255, 1)),
                  ),
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(6.w)),
                  onPressed: () {
                    Navigator.of(context).pop(1);
                  },
                ),
              ),
            )
          ],
        ),
      ),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 3), () {
      print('刷新');
      setState(() {});
      return null;
    });
  }
}