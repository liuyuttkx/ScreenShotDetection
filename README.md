# ScreenShotDetection

#### Description
屏幕截图监听
获取手动截图时，截图图片路径的Uri



#### Software Architecture
Software architecture description

#### Installation

1.  权限。7.0及以上必须获取存储权限，否则虽然能监听到截图事件，但无法获取图片路径。
2.  截图回调的图片路径需要使用Uri对象，由于安卓10及以上调整访问SD权限，使用文件路径直接访问图片是访问不了的。
3.  监听。当app切到后台时，可关闭截图监听，切后前台时，打开监听。另外，部分敏感界面，如账号登录，个人消息等信息界面可考虑屏蔽监听截图功能。

#### 使用

        //step1: 创建OnScreenShotDetection实现类对象
        mDetection = ScreenShotDetectionManager.create(this);
        //step2: 设置屏幕截图监听
        mDetection.setScreenShotChangeListener(new OnScreenShotNotifycationListener() {
            @Override
            public void onShot(String imagePath, Uri imageUri) {
                // imagePath 不能直接使用，由于安卓10系统及以上，限制了访问SD卡，需要使用ContentResolver访问。
                // 通过imagePath获取图片的Uri可以使用
                if (mShotIv != null) {
                    mShotIv.setImageURI(imageUri);
                }
                updateHint();
                Toast.makeText(MainActivity.this, "图片路径 ：" + imagePath.toString(), Toast.LENGTH_LONG).show();
            }
        });


    @Override
    protected void onResume() {
        super.onResume();
        if (mDetection != null) {
            //开启屏幕截图监听
            mDetection.startScreenShotDetection();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDetection != null) {
            //关闭屏幕截图监听
            mDetection.stopScreenShotDetection();
        }
    }

#### Contribution

1.  Fork the repository
2.  Create Feat_xxx branch
3.  Commit your code
4.  Create Pull Request


#### Gitee Feature

1.  You can use Readme\_XXX.md to support different languages, such as Readme\_en.md, Readme\_zh.md
2.  Gitee blog [blog.gitee.com](https://blog.gitee.com)
3.  Explore open source project [https://gitee.com/explore](https://gitee.com/explore)
4.  The most valuable open source project [GVP](https://gitee.com/gvp)
5.  The manual of Gitee [https://gitee.com/help](https://gitee.com/help)
6.  The most popular members  [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
