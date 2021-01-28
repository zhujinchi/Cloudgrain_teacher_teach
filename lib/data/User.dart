import 'package:event_bus/event_bus.dart';

class User {
  factory User() => shared();
  static User _instance;
  User._() {}
  static User shared() {
    if (_instance == null) {
      _instance = User._();
    }
    return _instance;
  }

  //用户基本数据
  String id;
  String userAccount;
  String userType;
  String userMail;
  String userImageId;
  String imgUrl;
  String mobilePhone;
  String actualName;
  String userSex;
  String loginLastAddr;
  String loginLastTime;
  String nickName;
  String createBy;
  String createDate;
  String updateBy;
  String updateDate;
  String remarks;
  String delFlag;
  //好开会数据
  String accessToken;
  String meetingAccount;
  String meetingPassword;
  String registered;
  Map userFamily;
  String publicTeacher;
  String userTeacher;
  String userStudent;
  //用户数据字典
  List<dynamic> sysDictionary;
  int sysDictionaryCount;
  //
  dynamic userTutorNight;
  double price;
  //String equipment;
  //孩子信息
  // int childCount;
  // int currentChild;
  // List<Map<String, dynamic>>
  //     childList; //例子；{userId: 1330506149584777218, childUserId: 1330513628725018625, childActualName: 洛雪, childNickName: 雪儿, userSex: 2, relation: 101}
  //晚辅导课程数据
  DateTime classTagDay;
  final eventBus = EventBus();

  String getDic(String type, String code) {
    for (int i = 0; i < User.shared().sysDictionaryCount; i++) {
      if (User.shared().sysDictionary[i]['type'] == type &&
          User.shared().sysDictionary[i]['code'] == code) {
        return User.shared().sysDictionary[i]['name'];
      }
    }
    return 'none';
  }

  //通知信息
  final notificationBus = EventBus();
}
