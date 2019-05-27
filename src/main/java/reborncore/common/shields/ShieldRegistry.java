/*
 * Copyright (c) 2018 modmuss50 and Gigabit101
 *
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package reborncore.common.shields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.util.Identifier;

/**
 * Created by Mark on 21/03/2016.
 */
public class ShieldRegistry {
	public static List<Shield> shieldList = new ArrayList<>();
	public static HashMap<String, Shield> shieldHashMap = new HashMap<>();
	public static HashMap<Shield, Identifier> shieldTextureHashMap = new HashMap<>();

	public static void registerShield(Shield shield) {
		shieldList.add(shield);
		shieldHashMap.put(shield.name, shield);
		shieldTextureHashMap.put(shield, shield.getShieldTexture());
	}

}