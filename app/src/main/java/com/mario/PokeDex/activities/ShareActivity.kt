package com.mario.PokeDex.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.mario.PokeDex.R
import com.mario.PokeDex.fragments.LandPokemonContent
import com.mario.PokeDex.models.Pokemon

class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        var pokemonInfo: Pokemon = intent?.extras?.getParcelable("Pokemon") ?: Pokemon()

        initActivity(pokemonInfo)
    }

    fun initActivity(pokemon: Pokemon) {
        var contentFragmet: LandPokemonContent = LandPokemonContent.newInstance(pokemon)
        changeFragment(R.id.content, contentFragmet)
        Log.d("fin", "fin")

    }

    private fun changeFragment(id: Int, frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, frag).commit()
    }

}
