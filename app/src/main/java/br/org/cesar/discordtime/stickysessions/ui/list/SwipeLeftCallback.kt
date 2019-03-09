package br.org.cesar.discordtime.stickysessions.ui.list

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.domain.model.Session
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SwipeLeftCallback(adapter: SessionAdapter) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private var mAdapter: SessionAdapter = adapter
    private var icon: Drawable
    private var background: ColorDrawable
    private val swipeSubject: PublishSubject<Session> = PublishSubject.create()
    var swipeEvent: Observable<Session> = swipeSubject

    init {
        icon = ContextCompat.getDrawable(mAdapter.context,
                R.drawable.baseline_calendar_today_white_24)!!
        background = ColorDrawable(mAdapter.context.getColor(R.color.colorAccent))
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        TODO("not implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val realPosition = viewHolder.layoutPosition % mAdapter.sessions.size
        swipeSubject.onNext(mAdapter.sessions[realPosition])
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight

        if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom)

        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
            icon.setBounds(0, 0, 0, 0)
        }

        background.draw(c)
        icon.draw(c)
    }
}