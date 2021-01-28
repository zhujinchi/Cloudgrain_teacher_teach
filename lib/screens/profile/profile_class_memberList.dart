import 'package:Cloudgrain_teacher_teach/data/User.dart';
import 'package:Cloudgrain_teacher_teach/widgets/class_comment_cell.dart';
import 'package:Cloudgrain_teacher_teach/widgets/class_memberlist_cell.dart';
import 'package:Cloudgrain_teacher_teach/widgets/network/dio_manager.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';

import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfileClassMemberListScreen extends StatefulWidget {
  String classTitle;
  String classId;

  ProfileClassMemberListScreen({Key key, this.classTitle, this.classId})
      : super(key: key);

  @override
  _ProfileClassMemberListScreenState createState() =>
      _ProfileClassMemberListScreenState();
}

class _ProfileClassMemberListScreenState
    extends State<ProfileClassMemberListScreen> {
  dynamic classMemberList = '';
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
          leading: IconButton(
            icon: const Icon(Icons.arrow_back_ios),
            color: Colors.grey,
            iconSize: 28.0,
            onPressed: () {
              Navigator.pop(context);
            },
          ),
          title: Text(
            this.widget.classTitle,
            style: TextStyle(
                color: Color.fromRGBO(15, 32, 67, 1),
                fontSize: 18.w,
                fontFamily: 'PingFangSC-Medium'),
          ),
        ),
        body: RefreshIndicator(
          onRefresh: _refresh,
          child: _buildClassMemberListScrollView(),
        ));
  }

  CustomScrollView _buildClassMemberListScrollView() {
    if (classMemberList == '') {
      return CustomScrollView(
        slivers: <Widget>[
          _buildEmptyView(),
        ],
      );
    } else {
      return CustomScrollView(
        slivers: <Widget>[
          _buildTitileView(),
          _buildCollectionClassList(),
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

  SliverToBoxAdapter _buildTitileView() {
    return SliverToBoxAdapter(
        child: Container(
      height: 65.w,
      color: Colors.white,
      child: Stack(
        children: <Widget>[
          Padding(
            padding: EdgeInsets.only(left: 34.w, top: 31.w),
            child: Container(
              width: 6.w,
              height: 12.w,
              decoration: BoxDecoration(
                color: Color.fromRGBO(0, 145, 255, 1),
                borderRadius: BorderRadius.circular(21.w),
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 52.w, top: 24.w),
            child: Text(
              '学生',
              style: TextStyle(
                  color: Color.fromRGBO(0, 145, 255, 1),
                  fontSize: 18.sp,
                  letterSpacing: 0.22.sp,
                  fontFamily: 'PingFangSC-Regular'),
            ),
          ),
          Padding(
            padding: EdgeInsets.only(left: 0.w, top: 74.w),
            child: Container(
              width: 1024.w,
              height: 1.w,
              color: Color.fromRGBO(246, 246, 246, 1),
            ),
          ),
        ],
      ),
    ));
  }

  SliverFixedExtentList _buildCollectionClassList() {
    return SliverFixedExtentList(
      itemExtent: 75.w,
      delegate:
          new SliverChildBuilderDelegate((BuildContext context, int index) {
        //创建列表项
        return new Container(
          alignment: Alignment.center,
          child: new Container(
            width: 1024.w,
            height: 75.w,
            color: Colors.white,
            child: ClassMemberListCell(
              memberImgUrl: classMemberList[index]['imgUrl'],
              memberName: classMemberList[index]['nickName'],
            ),
          ),
        );
      }, childCount: classMemberList.length
              //this.guidanceList.length //10个列表项
              ),
    );
  }

  Future<void> _refresh() async {
    await Future<Null>.delayed(Duration(seconds: 0), () {
      setDataOfClassMemberList();
      return null;
    });
  }

  void setDataOfClassMemberList() {
    //网络请求
    FormData params = FormData.fromMap({"id": this.widget.classId});

    DioManager.getInstance().setBaseUrl(1);
    //
    Map<String, dynamic> httpHeaders = {
      'Accept': 'application/json,*/*',
      'Content-Type': 'application/json',
      'accessToken': User.shared().accessToken,

      //'token': DioUtils.TOKEN
    };
    DioManager.getInstance().setHeaders(httpHeaders);
    DioManager.getInstance().get("/class/member", params, (result) {
      setState(() {
        classMemberList = result['data']['student'];
        print(classMemberList[0]);
      });
      //验证通过提交数据
    }, (error) {});
    //
  }
}
