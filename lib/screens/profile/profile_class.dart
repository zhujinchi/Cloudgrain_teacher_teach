import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:Cloudgrain_teacher_teach/widgets/profile_class_cell.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileClassScreen extends StatefulWidget {
  @override
  _ProfileClassScreenState createState() => _ProfileClassScreenState();
}

class _ProfileClassScreenState extends State<ProfileClassScreen> {
  dynamic classList = '';
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
              onTap: () {
                showCupertinoDialog(
                  context: context,
                  builder: (context) {
                    return CupertinoAlertDialog(
                      title: Text('无法建班'),
                      content: Text('\n晚辅导老师请在云课界面开课'),
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
              },
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
          child: _buildClassScrollView(),
        ));
  }

  CustomScrollView _buildClassScrollView() {
    if (classList == '') {
      return CustomScrollView(
        slivers: <Widget>[
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          // _buildEmptyView(),
          _buildTopView(),
          _buildClassList(),
          _buildBottomView(),
        ],
      );
    }
  }

  SliverFixedExtentList _buildClassList() {
    return SliverFixedExtentList(
      itemExtent: 218.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
              width: 722.w,
              height: 218.w,
              padding: EdgeInsets.fromLTRB(68.w, 19.w, 84.w, 19.w),
              color: Colors.white,
              child: ProfileClassCell(
                className: this.classList[index]['name'],
                bindStudentNumber: this.classList[index]['stuNum'].toString(),
                bindParentsNumber:
                    this.classList[index]['actualStuNum'].toString(),
                classId: this.classList[index]['classId'].toString(),
                classTime: classTime(index),
              )),
        );
      }, childCount: classList.length),
    );
  }

  String classTime(int index) {
    String startday =
        this.classList[index]['startDate'].toString().substring(0, 10);
    String endday =
        this.classList[index]['endDate'].toString().substring(0, 10);

    return startday + ' 至 ' + endday;
  }

  Widget _buildTopView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 19.w,
        color: Colors.white,
      ),
    );
  }

  Widget _buildBottomView() {
    return SliverToBoxAdapter(
      child: Container(
        height: 62.w,
        color: Colors.white,
      ),
    );
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
                '您当前没有班级',
                style: TextStyle(
                    color: Color.fromRGBO(15, 32, 67, 1),
                    fontSize: 18.sp,
                    fontFamily: 'PingFangSC-Regular'),
              ),
            ),
            Padding(
              padding: EdgeInsets.only(left: 343.w, top: 256.w),
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
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfClassList();
      return null;
    });
  }

  void setDataOfClassList() {
    //网络请求
    FormData params = FormData.fromMap({});

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().post("/class/teacher/courses", params, (result) {
      setState(() {
        print(result);
        this.classList = result['data'];
        //{id: 1341736803850358786, classId: 1341736803837775874, classType: 1, name: 0晚辅导课程测试, subjectCode: null, stuNum: 3, actualStuNum: 0, startDate: 2020-12-27 00:00:00, endDate: 2021-02-26 00:00:00, amount: 12, unitPrice: 0.05, totalPrice: 0.15, depict: 1, createBy: 1322815240747462658, createDate: 2020-12-23 21:25:57, updateBy: null, updateDate: null, remarks: null, delFlag: 0}
      });
      //验证通过提交数据
    }, (error) {});
    //
  }
}
