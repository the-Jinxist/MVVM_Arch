package com.neo.testtutorial

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Creating our specific test activity because
 * ..the one that comes with Hilt doesn't have the
 * .@AndroidEntryPoint annotation on it
 *
 */

@AndroidEntryPoint
class HiltTestActivity: AppCompatActivity()