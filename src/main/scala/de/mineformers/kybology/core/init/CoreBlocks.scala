/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 MineFormers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.mineformers.kybology.core.init

import de.mineformers.core.mod.BlockHolder
import de.mineformers.core.registry.SharedBlockRegistry
import de.mineformers.kybology.Core.Names
import de.mineformers.kybology.core.block.{BlockCrystal, BlockRotatable, BlockRift}

/**
 * CoreBlocks
 *
 * @author PaleoCrafter
 */
class CoreBlocks extends BlockHolder {
  val rift = new BlockRift
  val rotatable = new BlockRotatable
  val crystal = new BlockCrystal

  override def init(): Unit = {
    SharedBlockRegistry.add(Names.Blocks.Rift, rift)
    SharedBlockRegistry.add(Names.Blocks.Crystal, crystal)
    SharedBlockRegistry.add("rotatable", rotatable)
  }
}
