import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/home_guidance_finishedHomework_cell.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class HomeWorkDetailsScreen extends StatefulWidget {
  final String homeWorkId;

  HomeWorkDetailsScreen({
    Key key,
    this.homeWorkId,
  }) : super(key: key);
  @override
  _HomeWorkDetailsScreenState createState() => _HomeWorkDetailsScreenState();
}

class _HomeWorkDetailsScreenState extends State<HomeWorkDetailsScreen>
    with SingleTickerProviderStateMixin {
  dynamic homeWorkDetailList = '';
  @override
  void initState() {
    _refresh();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    ScreenUtil.init(context,
        designSize: Size(1024, 768), allowFontScaling: false);
    return Scaffold(
        backgroundColor: Colors.black,
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
            '作业详情',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: _buildFinishedHomeWorkScrollView(),
        ));
  }

  CustomScrollView _buildFinishedHomeWorkScrollView() {
    if (homeWorkDetailList.length == 0) {
      return CustomScrollView(
        slivers: <Widget>[
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          _buildHomeworkDetailsList(),
        ],
      );
    }
  }

  SliverToBoxAdapter _buildEmptyView() {
    return SliverToBoxAdapter(
      child: Container(
        width: 1024.w,
        height: 568.w,
        color: Colors.white,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 464.w, top: 185.w),
              child: Image.asset(
                'assets/images/yk_illustration_a@3x.png',
                width: 96.w,
                height: 96.w,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 443.w, top: 286.w),
              child: Text(
                '暂时没有作业详情',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
          ],
        ),
      ),
    );
  }

  SliverFixedExtentList _buildHomeworkDetailsList() {
    return SliverFixedExtentList(
      itemExtent: 740.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
              width: 1024.w,
              height: 740.w,
              padding: EdgeInsets.fromLTRB(24.w, 18.w, 24.w, 18.w),
              color: Colors.black,
              child: Container(
                width: 1024.w,
                height: 740.w,
                decoration: BoxDecoration(
                  color: Colors.black,
                  image: DecorationImage(
                      image: NetworkImage(
                        //this.memberImgUrl,
                        homeWorkDetailList[index]['outerLink'],
                      ),
                      fit: BoxFit.contain),
                  //borderRadius: BorderRadius.circular(21.w),
                ),
              )),
        );
      }, childCount: homeWorkDetailList.length),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfHomeWorkCards();
      return null;
    });
  }

  //网络请求
  void setDataOfHomeWorkCards() {
    //网络请求
    FormData params = FormData.fromMap({
      'id': this.widget.homeWorkId,
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
    DioManager.getInstance().get("/taskFinishRec/get", params, (result) {
      setState(() {
        homeWorkDetailList = result['data']['files'];
        print(homeWorkDetailList[0]);
      });

      //验证通过提交数据
    }, (error) {
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('接口错误'),
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
