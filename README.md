

<h4> 主要演示多级联动与多种手势冲突的功能，使用CoordinatorLayout和AppBarLayout实现联动 </h4>

这是效果图片，GIF压缩的有些失真了

<img src="https://github.com/CarGuo/linkagescroll/blob/master/device-2016-11-10-151539.gif" width="240px" height="426px"/>

<h4>* 界面由两个viewpager组成，有顶部的banner是底部的viewPager,viewPager里嵌套了recyclerView，recyclerView有上拉加载更多</h4>

<h4>* 外部使用了SwipeRefreshLayout实现刷新功能</h4>

<h4>* PagerSlidingTabStrip实现了tab的停靠与viewPager的联动切换 </h4>

<h4>* banner和tab有alpha和移动效果，viewpager与刷新与tab之间实现联动</h4>






