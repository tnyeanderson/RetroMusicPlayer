package code.name.monkey.retromusic.fragments.artists

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import code.name.monkey.retromusic.R
import code.name.monkey.retromusic.adapter.artist.ArtistAdapter
import code.name.monkey.retromusic.fragments.base.AbsLibraryPagerRecyclerViewCustomGridSizeFragment
import code.name.monkey.retromusic.interfaces.MainActivityFragmentCallbacks
import code.name.monkey.retromusic.util.PreferenceUtilKT

class ArtistsFragment :
    AbsLibraryPagerRecyclerViewCustomGridSizeFragment<ArtistAdapter, GridLayoutManager>(),
    MainActivityFragmentCallbacks {

    lateinit var artistViewModel: ArtistViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artistViewModel = ViewModelProvider(this).get(ArtistViewModel::class.java)
        artistViewModel.artists.observe(viewLifecycleOwner, Observer { artists ->
            if (artists.isNotEmpty()) {
                adapter?.swapDataSet(artists)
            } else {
                adapter?.swapDataSet(listOf())
            }
        })
    }

    override fun handleBackPress(): Boolean {
        return false
    }

    override val emptyMessage: Int
        get() = R.string.no_artists

    override fun onMediaStoreChanged() {
        artistViewModel.loadArtists()
    }

    override fun setSortOrder(sortOrder: String) {
        artistViewModel.loadArtists()
    }

    override fun createLayoutManager(): GridLayoutManager {
        return GridLayoutManager(requireActivity(), getGridSize())
    }

    override fun createAdapter(): ArtistAdapter {
        val dataSet = if (adapter == null) ArrayList() else adapter!!.dataSet
        return ArtistAdapter(
            mainActivity,
            dataSet,
            itemLayoutRes(),
            mainActivity
        )
    }

    override fun loadGridSize(): Int {
        return PreferenceUtilKT.artistGridSize
    }

    override fun saveGridSize(gridColumns: Int) {
        PreferenceUtilKT.artistGridSize = gridColumns
    }

    override fun loadGridSizeLand(): Int {
        return PreferenceUtilKT.artistGridSizeLand
    }

    override fun saveGridSizeLand(gridColumns: Int) {
        PreferenceUtilKT.artistGridSizeLand = gridColumns
    }

    override fun setGridSize(gridSize: Int) {
        layoutManager?.spanCount = gridSize
        adapter?.notifyDataSetChanged()
    }

    override fun loadSortOrder(): String {
        return PreferenceUtilKT.artistSortOrder
    }

    override fun saveSortOrder(sortOrder: String) {
        PreferenceUtilKT.artistSortOrder = sortOrder
    }

    override fun loadLayoutRes(): Int {
        return PreferenceUtilKT.artistGridStyle
    }

    override fun saveLayoutRes(layoutRes: Int) {
        PreferenceUtilKT.artistGridStyle = layoutRes
    }

    companion object {
        @JvmField
        val TAG: String = ArtistsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): ArtistsFragment {
            return ArtistsFragment()
        }
    }
}