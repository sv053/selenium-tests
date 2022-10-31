/*
 * Copyright 2002-2021 Solera.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package air.airlineservice.service.airline;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 *  Holds base64 image data.
 */
@Embeddable
public class Image {

    @Lob
    @Column(name="image_data", nullable = false)
    @NotNull(message = "Image data is mandatory")
    private String data;

    /**
     * Constructs a new Image.
     */
    public Image() {
    }

    /**
     * Constructs a new Image with the specified base64 data.
     *
     * @param data dase64 data
     */
    public Image(String data) {
        this.data = data;
    }

    /**
     * Constructs a new Image copying data from the passed one.
     *
     * @param other Image to copy data from
     */
    public Image(Image other) {
        data = other.data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Image image = (Image) other;
        return Objects.equals(data, image.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "data='" + data + '\'' +
                '}';
    }
}
