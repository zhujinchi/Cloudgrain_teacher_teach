import 'package:Cloudgrain_teacher_teach/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class CloudClassScreen extends StatefulWidget {
  @override
  _CloudClassScreenState createState() => _CloudClassScreenState();
}

class _CloudClassScreenState extends State<CloudClassScreen>
    with SingleTickerProviderStateMixin {
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
          leading: IconButton(
            icon: const Icon(Icons.arrow_back_ios),
            color: Colors.grey,
            iconSize: 28.0,
            onPressed: () {
              Navigator.pop(context);
            },
          ),
          title: Text(
            '课程',
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
      child: Center(
          child: RaisedButton(
        color: Colors.blueAccent,
        child: Container(
          padding: EdgeInsets.only(left: 20, right: 20, top: 20, bottom: 20),
          child: Text(
            '点击按钮调用原生Android的好开会界面\n，返回值在控制台输出',
            style: TextStyle(color: Colors.white),
          ),
        ),
        onPressed: () async {
          const platform = const MethodChannel("toJava");
          String returnValue =
              await platform.invokeMethod("37e315e338c1d032418ea0911838a916");
          print("从原生Android的java方法返回的值是：" + returnValue);
        },
      )),
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
