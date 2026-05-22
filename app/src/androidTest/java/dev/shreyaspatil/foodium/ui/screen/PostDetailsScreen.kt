package dev.shreyaspatil.foodium.ui.screen

import com.kaspersky.kaspresso.screens.KScreen
import dev.shreyaspatil.foodium.R
import dev.shreyaspatil.foodium.ui.details.PostDetailsActivity
import io.github.kakaocup.kakao.text.KTextView
import io.github.kakaocup.kakao.toolbar.KToolbar

object PostDetailsScreen : KScreen<PostDetailsScreen>() {

    override val layoutId: Int = R.layout.activity_post_details
    override val viewClass: Class<*> = PostDetailsActivity::class.java

    val toolbar = KToolbar { withId(R.id.toolbar) }

    val postTitle = KTextView { withId(R.id.post_title) }

    val postAuthor = KTextView { withId(R.id.post_author) }

    val postBody = KTextView { withId(R.id.post_body) }
}
