/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.event.dnd;

import java.util.Optional;

import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

/**
 * Server side drop event. Fired when an HTML5 drop happens.
 *
 * @param <T>
 *         Type of the drop target component.
 * @author Vaadin Ltd
 * @see DropTargetExtension#addDropListener(DropListener)
 * @since 8.1
 */
public class DropEvent<T extends AbstractComponent> extends Component.Event {
    private final String dataTransferText;
    private final DragSourceExtension<? extends AbstractComponent> dragSourceExtension;
    private final AbstractComponent dragSource;

    /**
     * Creates a server side drop event.
     *
     * @param target
     *         Component that received the drop.
     * @param dataTransferText
     *         Data of type {@code "text"} from the {@code DataTransfer}
     *         object.
     * @param dragSourceExtension
     *         Drag source extension of the component that initiated the drop
     *         event.
     */
    public DropEvent(T target, String dataTransferText,
            DragSourceExtension<? extends AbstractComponent> dragSourceExtension) {
        super(target);

        this.dataTransferText = dataTransferText;

        this.dragSourceExtension = dragSourceExtension;
        this.dragSource = Optional.ofNullable(dragSourceExtension)
                .map(DragSourceExtension::getParent).orElse(null);
    }

    /**
     * Get data of type {@code "text"} from the client side {@code DataTransfer}
     * object.
     *
     * @return Data of type {@code "text"} if exists in the client side {@code
     * DataTransfer} object, otherwise {@literal null}.
     */
    public String getDataTransferText() {
        return dataTransferText;
    }

    /**
     * Returns the drag source component if the drag originated from a
     * component in the same UI as the drop target component, or an empty
     * optional.
     *
     * @return Drag source component or an empty optional.
     */
    public Optional<AbstractComponent> getDragSourceComponent() {
        return Optional.ofNullable(dragSource);
    }

    /**
     * Returns the extension of the drag source component if the drag
     * originated from a component in the same UI as the drop target component,
     * or an empty optional.
     *
     * @return Drag source extension or an empty optional
     */
    public Optional<DragSourceExtension<? extends AbstractComponent>> getDragSourceExtension() {
        return Optional.ofNullable(dragSourceExtension);
    }

    /**
     * Gets the server side drag data. This data can be set during the drag
     * start event on the server side and can be used to transfer data between
     * drag source and drop target when they are in the same UI.
     *
     * @return Optional server side drag data if set and the drag source and the
     * drop target are in the same UI, otherwise empty {@code Optional}.
     * @see DragSourceExtension#setDragData(Object)
     */
    public Optional<Object> getDragData() {
        return getDragSourceExtension().map(DragSourceExtension::getDragData);
    }

    /**
     * Returns the drop target component where the drop event occurred.
     *
     * @return Component on which a drag source was dropped.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getComponent() {
        return (T) super.getComponent();
    }
}
