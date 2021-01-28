import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/home_guidance_finishedHomework_cell.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class FinishedHomeWorkScreen extends StatefulWidget {
  @override
  _FinishedHomeWorkScreenState createState() => _FinishedHomeWorkScreenState();
}

class _FinishedHomeWorkScreenState extends State<FinishedHomeWorkScreen>
    with SingleTickerProviderStateMixin {
  bool isSearch;
  String searchText;
  dynamic finishedHomeWorkList = '';

  final _searchEditingController = TextEditingController();
  @override
  void initState() {
    isSearch = false;
    _refresh();
    super.initState();
  }

  @override
  void dispose() {
    _searchEditingController.dispose();
    super.dispose();
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
            '批改作业',
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: GestureDetector(
            onTap: () {
              FocusScopeNode currentFocus = FocusScope.of(context);
              if (!currentFocus.hasPrimaryFocus &&
                  currentFocus.focusedChild != null) {
                FocusManager.instance.primaryFocus.unfocus();
              }
            },
            child: RefreshIndicator(
              onRefresh: _refresh,
              child: _buildFinishedHomeWorkScrollView(),
            )));
  }

  CustomScrollView _buildFinishedHomeWorkScrollView() {
    if (finishedHomeWorkList.length == 0) {
      return CustomScrollView(
        slivers: <Widget>[
          _buildTopView(),
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          _buildTopView(),
          _buildFinishedHomeworkList(),
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
              padding: EdgeInsets.only(left: 463.w, top: 286.w),
              child: Text(
                '暂时没有学生',
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

  SliverToBoxAdapter _buildTopView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 106.w,
        child: Stack(
          children: <Widget>[
            Padding(
              padding: EdgeInsets.only(left: 491.w, top: 16.w),
              child: Text(
                '待批改',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 14.sp,
                    fontWeight: FontWeight.bold,
                    letterSpacing: 0.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
                padding: EdgeInsets.only(left: 247.w, top: 75.w),
                child: Row(
                  children: <Widget>[
                    Text(
                      '当前班级：',
                      style: TextStyle(
                          color: Color.fromRGBO(155, 157, 161, 1),
                          fontSize: 14.sp,
                          letterSpacing: 0.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                    Text(
                      '三年级2班',
                      style: TextStyle(
                          color: Color.fromRGBO(0, 145, 255, 1),
                          fontSize: 14.sp,
                          letterSpacing: 0.sp,
                          fontFamily: 'PingFangSC-Regular'),
                    ),
                  ],
                )),
            Padding(
                padding: EdgeInsets.only(left: 560.w, top: 53.w, right: 310.w),
                child: Container(
                  child: Stack(
                    children: <Widget>[
                      TextField(
                        controller: this._searchEditingController,
                        //maxLength: 2,
                        autofocus: false,
                        style: TextStyle(fontSize: 12.sp),
                        decoration: InputDecoration(
                            contentPadding: EdgeInsets.only(
                              left: 0.w,
                            ),
                            border: InputBorder.none,
                            hintText: '输入待查询信息',
                            hintStyle: TextStyle(
                                color: Color.fromRGBO(155, 157, 161, 1),
                                fontFamily: 'PingFangSC-Medium',
                                fontSize: 12.sp)),
                        onChanged: (value) {
                          setState(() {
                            this.searchText = value;
                          });
                        },
                      ),
                      Padding(
                          padding: EdgeInsets.only(top: 40.w),
                          child: Container(
                            width: 1000.w,
                            height: 1.w,
                            color: Color.fromRGBO(201, 204, 210, 1),
                          ))
                    ],
                  ),
                )),
            Padding(
              padding: EdgeInsets.only(left: 720.w, top: 68.w),
              child: Container(
                color: Colors.white,
                width: 60.w,
                height: 26.w,
                padding: const EdgeInsets.fromLTRB(0, 0, 0, 0),
                child: MaterialButton(
                    child: Text(
                      "搜索",
                      style: TextStyle(
                        color: Color.fromRGBO(255, 255, 255, 1),
                        fontFamily: 'PingFangSC-Semibold',
                        fontSize: 10.sp,
                      ),
                    ),
                    color: Color.fromRGBO(104, 212, 185, 1),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(3.w)),
                    //borderSide: BorderSide(color: Colors.orange, width: 1),
                    onPressed: () {
                      setState(() {
                        isSearch = true;
                        setDataOfHomeWorkCards();
                      });
                    }),
              ),
            )
          ],
        ),
      ),
    );
  }

  SliverFixedExtentList _buildFinishedHomeworkList() {
    return SliverFixedExtentList(
      itemExtent: 492.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
              width: 540.w,
              height: 492.w,
              padding: EdgeInsets.fromLTRB(0, 16.w, 0, 9.w),
              color: Colors.white,
              child: GuidanceFinishedHomeWorkCell(
                homeWorkId: finishedHomeWorkList[index]['id'].toString(),
                homeWorkType:
                    finishedHomeWorkList[index]['taskSubjectId'].toString(),
                homeWorkContent:
                    finishedHomeWorkList[index]['content'].toString(),
                studentName:
                    finishedHomeWorkList[index]['studentName'].toString(),
                studentClass: finishedHomeWorkList[index]['schoolName']
                        .toString() +
                    finishedHomeWorkList[index]['schoolGradeName'].toString() +
                    '年级' +
                    finishedHomeWorkList[index]['className'].toString() +
                    '班',
                spendTime:
                    finishedHomeWorkList[index]['costTimeStr'].toString(),
                startTime: finishedHomeWorkList[index]['startTime'].toString(),
                finishedTime: finishedHomeWorkList[index]['endTime'].toString(),
                homeWorkImageUrl: finishedHomeWorkList[index]['files'].length ==
                        0
                    ? 'https://yundou.skyline.name/static/files/20210123155756/6752036163253104640.jpg'
                    : finishedHomeWorkList[index]['files'][0]['outerLink']
                        .toString(),
              )),
        );
      }, childCount: finishedHomeWorkList.length),
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
    FormData params;
    if (isSearch) {
      params = FormData.fromMap({
        'pageNum': 1,
        'pageSize': 1000,
        'content': this._searchEditingController.text,
      });
    } else {
      params = FormData.fromMap({
        'pageNum': 1,
        'pageSize': 1000,
      });
    }

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().get("/taskFinishRec/findPage", params, (result) {
      setState(() {
        this.finishedHomeWorkList = result['data']['records'];
        _searchEditingController.text = '';
        this.isSearch = false;
      });

      //验证通过提交数据
    }, (error) {
      showCupertinoDialog(
        context: context,
        builder: (context) {
          return CupertinoAlertDialog(
            title: Text('搜索错误'),
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
