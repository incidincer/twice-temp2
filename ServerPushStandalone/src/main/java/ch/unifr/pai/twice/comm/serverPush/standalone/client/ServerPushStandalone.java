package ch.unifr.pai.twice.comm.serverPush.standalone.client;

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
import ch.unifr.pai.twice.comm.serverPush.client.ServerPushEventBus;
import ch.unifr.pai.twice.comm.serverPush.shared.PingEvent;
import ch.unifr.pai.twice.widgets.client.MultiFocusTextBox;
import ch.unifr.pai.twice.widgets.client.MyNewTextBox;
import ch.unifr.pai.twice.widgets.client.RemoteKeyRecorder;
import ch.unifr.pai.twice.widgets.client.RemoteMultiFocusTextBox;
import ch.unifr.pai.twice.widgets.client.events.BlockingTestEvent;
import ch.unifr.pai.twice.widgets.client.events.BlockingTestEvent.BlockingTestHandler;
import ch.unifr.pai.twice.widgets.client.events.DiscardingTestEvent;
import ch.unifr.pai.twice.widgets.client.events.DiscardingTestEvent.DiscardingTestHandler;
import ch.unifr.pai.twice.widgets.client.events.RemoteKeyPressEvent;
import ch.unifr.pai.twice.widgets.client.events.UndoableTestEvent;
import ch.unifr.pai.twice.widgets.client.events.UndoableTestEvent.UndoableTestHandler;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * An example application showing the functionality of the server push event bus
 * 
 * @author Oliver Schmid
 * 
 */
public class ServerPushStandalone implements EntryPoint {

	ServerPushEventBus eventBus = new ServerPushEventBus();

	/**
	 * This is the entry point method.
	 */
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@Override
	public void onModuleLoad() {

		//
		//
		//
		//
		FlexTable table = new FlexTable();
		final TextBox message = new TextBox();
		table.setWidget(0, 0, new Label("Message"));
		table.setWidget(0, 1, message);
		table.setWidget(1, 0, new Label("Event date"));
		final DatePicker datePicker = new DatePicker();
		table.setWidget(1, 1, datePicker);

		Button undoable = new Button("Send as undoable event", new ClickHandler() {

			/**
			 * Creates an {@link UndoableTestEvent} and fires it on the event bus.
			 * 
			 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
			 */
			@Override
			public void onClick(ClickEvent event) {
				UndoableTestEvent e = GWT.create(UndoableTestEvent.class);
				e.setFoo(message.getText());
				if (datePicker.getValue() != null)
					e.setTimestamp(datePicker.getValue().getTime());
				eventBus.fireEvent(e);
			}
		});

		Button blocking = new Button("Send as blocking event", new ClickHandler() {

			/**
			 * Creates an {@link BlockingTestEvent} and fires it on the event bus.
			 * 
			 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
			 */
			@Override
			public void onClick(ClickEvent event) {
				BlockingTestEvent e = GWT.create(BlockingTestEvent.class);
				e.foo = message.getText();
				if (datePicker.getValue() != null)
					e.setTimestamp(datePicker.getValue().getTime());
				eventBus.fireEvent(e);
			}
		});

		Button discarding = new Button("Send as discarding event", new ClickHandler() {

			/**
			 * Creates an {@link DiscardingTestEvent} and fires it on the event bus.
			 * 
			 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
			 */
			@Override
			public void onClick(ClickEvent event) {
				DiscardingTestEvent e = GWT.create(DiscardingTestEvent.class);
				e.setInstanceId("eventTests");
				e.setFoo(message.getText());
				if (datePicker.getValue() != null)
					e.setTimestamp(datePicker.getValue().getTime());
				eventBus.fireEvent(e);
			}
		});

		final HTML html = new HTML("");
		final HTML value = new HTML("");
		eventBus.addHandler(BlockingTestEvent.TYPE, new BlockingTestHandler() {

			/**
			 * listens for blocking test events and adds their message to the display
			 * 
			 * @param event
			 */
			@Override
			public void onEvent(BlockingTestEvent event) {
				html.setHTML(html.getHTML() + " [BLOCKING " + event.getTimestamp() + "] " + event.foo);
				value.setHTML(event.foo);
			}
		});
		eventBus.addHandler(UndoableTestEvent.TYPE, new UndoableTestHandler() {

			/**
			 * undo the event in case of a conflict by resetting the display to the values valid before the event has been applied
			 * 
			 * @param event
			 */
			@Override
			public void undo(UndoableTestEvent event) {
				html.setHTML(event.getOldHistory());
				value.setHTML(event.getOldValue());
			}

			/**
			 * listens for undoable events and adds their message to the display
			 * 
			 * @param event
			 */
			@Override
			public void onEvent(UndoableTestEvent event) {
				html.setHTML(html.getHTML() + " [UNDOABLE " + event.getTimestamp() + "] " + event.getFoo());
				value.setHTML(event.getFoo());
			}

			/**
			 * stores the state of the display before the newly arrived event has arrived
			 * 
			 * @param event
			 */
			@Override
			public void saveState(UndoableTestEvent event) {
				event.setOldHistory(html.getHTML());
				event.setOldValue(value.getHTML());
			}
		});

		eventBus.addHandler(DiscardingTestEvent.TYPE, new DiscardingTestHandler() {

			/**
			 * listens for discarding test events and adds their message to the display
			 * 
			 * @param event
			 */
			@Override
			public void onEvent(DiscardingTestEvent event) {
				html.setHTML(html.getHTML() + " [DISCARDING " + event.getTimestamp() + "] " + event.getFoo());
				value.setHTML(event.getFoo());
			}
		});

		table.setWidget(2, 0, undoable);
		table.setWidget(2, 1, blocking);
		table.setWidget(2, 2, discarding);
		table.setWidget(3, 0, new Label("Current value: "));
		table.setWidget(3, 1, value);
		table.setWidget(4, 0, new Label("Event history: "));
		table.setWidget(4, 1, html);

		Button ping = new Button("Send ping", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PingEvent e = GWT.create(PingEvent.class);
				e.setInstanceId("eventTests");
				if (datePicker.getValue() != null)
					e.setTimestamp(datePicker.getValue().getTime());
				eventBus.fireEvent(e);
			}
		});

		RootPanel.get().add(table);
		RootPanel.get().add(ping);

		RootPanel.get().add(new Label("GWTEvent wrapper"));
		FlexTable table2 = new FlexTable();

		final TextBox box = new TextBox();
		table2.setWidget(0, 0, new Label("Textbox"));
		table2.setWidget(0, 1, box);

		eventBus.addHandlerToSource(RemoteKeyPressEvent.TYPE, "test", new RemoteKeyPressEvent.Handler() {

			@Override
			public void onEvent(RemoteKeyPressEvent event) {
				box.setValue(box.getValue() + event.charCode);
			}
		});
		box.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				RemoteKeyPressEvent e = GWT.create(RemoteKeyPressEvent.class);
				e.wrap(event);
				eventBus.fireEventFromSource(e, "test");
				event.preventDefault();
			}
		});

		final MyNewTextBox box4 = new MyNewTextBox(eventBus, "myNewTextBox");
		table2.setWidget(1, 0, new Label("My new textbox"));
		table2.setWidget(1, 1, box4);

		final RemoteKeyRecorder recorder = new RemoteKeyRecorder("multiFocus", eventBus);
		RootPanel.get().add(recorder);

		// final RemoteTextBox box3 = new RemoteTextBox("multiFocus", eventBus);
		// box3.setValue("");
		// RootPanel.get().add(box3);
		// table2.setWidget(2, 0, new Label("Textbox (undoable - other resource)"));
		// table2.setWidget(2,1,box3);
		// RootPanel.get().add(table2);
		//
		MultiFocusTextBox multiFocus = new RemoteMultiFocusTextBox("multiFocus", eventBus);
		RootPanel.get().add(multiFocus);

	}
}
