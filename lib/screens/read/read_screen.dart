import 'package:Cloudgrain_teacher_teach/widgets/study_bookList.dart';
import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ReadScreen extends StatefulWidget {
  @override
  _ReadScreenState createState() => _ReadScreenState();
}

class _ReadScreenState extends State<ReadScreen>
    with SingleTickerProviderStateMixin {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return DefaultTabController(
        length: 2,
        child: Scaffold(
          //backgroundColor: Colors.white,
          appBar: AppBar(
            backgroundColor: Colors.white,
            brightness: Brightness.light,
            elevation: 0.8,
            //flexibleSpace: SafeArea(child: null),
            automaticallyImplyLeading: false,
            title: Container(
              height: 48.w,
              //color: Colors.red,
              padding: EdgeInsets.fromLTRB(80.w, 10.w, 80.w, 0),
              child: TabBar(
                  indicatorSize: TabBarIndicatorSize.label,
                  unselectedLabelColor: Color.fromRGBO(0, 0, 0, 0.85),
                  labelStyle: TextStyle(
                      color: Color.fromRGBO(0, 145, 255, 1),
                      fontSize: 14.sp,
                      fontFamily: 'PingFangSC-Regular'),
                  labelColor: Color.fromRGBO(0, 145, 255, 1),
                  tabs: <Tab>[
                    Tab(
                      text: '语文',
                    ),
                    Tab(
                      text: '英语',
                    )
                  ]),
            ),
          ),
          body: TabBarView(
            children: <Widget>[
              Container(
                child: ListViewBookPage(),
              ),
              Container(
                child: ListViewBookPage(),
              ),
            ],
          ),
        ));
  }
}
