package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this
        Log.i("GameFragment", "ViewModelProvider called")


        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { isGameFinished ->
            if(isGameFinished) {
                gameFinished()
                viewModel.onGameFinishComplete()
            }
        })
        return binding.root
    }

    // Called when the game is finished
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
        //Toast.makeText(this.activity,"finished!", Toast.LENGTH_SHORT).show()
    }

    /** Methods for updating the UI **/
    private fun updateWordText(word: String) {
        binding.wordText.text = word
    }

    private fun updateScoreText(score: String) {
        binding.scoreText.text = score
    }

}