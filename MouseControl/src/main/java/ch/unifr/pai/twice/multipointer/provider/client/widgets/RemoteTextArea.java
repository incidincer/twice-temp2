package ch.unifr.pai.twice.multipointer.provider.client.widgets;
/*
 * Copyright 2013 Oliver Schmid
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import java.lang.String;

import ch.unifr.pai.twice.multipointer.provider.client.NoMultiCursorController;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.KeyCodes;

import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;

import com.google.gwt.user.client.ui.TextArea;

public class RemoteTextArea extends TextArea {


	public RemoteTextArea() {
		super();
		extend();
	}

	public RemoteTextArea(Element element) {
		super(element);
		extend();
	}

	private void extend() {
		
		this.addKeyPressHandler(new KeyPressHandler() { 

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (NoMultiCursorController.getUUID(event.getNativeEvent()) != null) {
					if (getValue() == null)
						setValue(String.valueOf(event.getCharCode()));
					else if (getCursorPos() <= getValue().length()) {
						setValue(getValue().substring(0, getCursorPos())
								+ event.getCharCode()    // fix double char type
								+ ((getCursorPos() == getValue().length()) ? ""
										: getValue().substring(getCursorPos())));
					}
				}
			}
		});
		
		
		this.addKeyUpHandler(new KeyUpHandler() { 

			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				int cursorPos = getCursorPos();
				if (NoMultiCursorController.getUUID(event.getNativeEvent()) != null) {
					switch (event.getNativeKeyCode()) {

					case KeyCodes.KEY_DELETE:
						if (getValue() != null
								&& cursorPos < getValue().length()) {
							setValue(getValue().substring(0, cursorPos)
									+ getValue().substring(cursorPos + 1));
						}
						break;
					case KeyCodes.KEY_BACKSPACE:
						if (getValue() != null && cursorPos > 0
								&& cursorPos <= getValue().length()) {
							setValue(getValue().substring(0, cursorPos-1) //cursorPos+1  
									+ getValue().substring(cursorPos)); //cursorpos+1   
							setCursorPos(cursorPos-1);  //cursorPos
						}
						break;
					}
				}
			}
		});
	}

//	public void processInput(String uuid, char c){
		
		
//		int pos= getCursorPos();
//		if (origincursor != null) {
//			pos = origincursor.position;
//		}
//		else {
//			pos = getValue() != null ? getValue().length() : 0;
//		}
//		setValue(String.valueOf(c));
//		if (getValue() == null)
//			setValue(String.valueOf(c));
//		else if (pos <= getValue().length()) {
//			setValue(getValue().substring(0, pos) + c + ((pos == getValue().length()) ? "" : getValue().substring(pos)));
//		}
//		for (Cursor cursor : cursors.values()) {
//			if (pos <= cursor.getPosition()) {
//				cursor.setPosition(cursor.getPosition() + 1);
//			}
//		}
		//scrollIfNecessary();
//	}
	
}
