import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileClassScreen extends StatefulWidget {
  @override
  _ProfileClassScreenState createState() => _ProfileClassScreenState();
}

class _ProfileClassScreenState extends State<ProfileClassScreen> {
  @override
  void initState() {
    super.initState();
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
            '我的班级',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
          actions: <Widget>[
            InkWell(
              onTap: () {},
              child: Center(
                child: Text(
                  '创建班级   ',
                  style: TextStyle(
                      color: Color.fromRGBO(0, 145, 255, 1),
                      fontSize: 16.sp,
                      fontFamily: 'PingFangSC-Regular'),
                ),
              ),
              highlightColor: Colors.transparent, // 透明色
              splashColor: Colors.transparent, // 透明色
            )
          ],
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
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 212.w, top: 265.w),
              child: Image.asset(
                'assets/images/yk_illustration_a@3x.png',
                width: 96.w,
                height: 96.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 287.w),
              child: Text(
                '您当前没有班级',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 316.w),
              child: Text(
                '开始创建班级吧～',
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

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 3), () {
      print('刷新');
      setState(() {});
      return null;
    });
  }
}
