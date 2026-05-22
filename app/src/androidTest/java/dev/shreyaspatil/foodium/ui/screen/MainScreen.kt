package dev.shreyaspatil.foodium.ui.screen

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.ui.main.MainActivity
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.swiperefresh.KSwipeRefreshLayout
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    val swipeRefreshLayout = KSwipeRefreshLayout { withId(R.id.swipeRefreshLayout) }

    val recyclerView = KRecyclerView(
        builder = { withId(R.id.postsRecyclerView) },
        itemTypeBuilder = { itemType(::PostItem) }
    )

    val networkStatusLayout = KView { withId(R.id.networkStatusLayout) }

    val textViewNetworkStatus = KTextView { withId(R.id.textViewNetworkStatus) }

    class PostItem(parent: Matcher<View>) : KRecyclerItem<PostItem>(parent) {
        val title = KTextView(parent) { withId(R.id.post_title) }
        val author = KTextView(parent) { withId(R.id.post_author) }
    }
}
