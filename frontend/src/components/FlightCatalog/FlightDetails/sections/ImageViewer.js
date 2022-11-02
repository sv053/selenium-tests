import React, { useState } from 'react'
import PropTypes from "prop-types"
import Carousel from 'react-bootstrap/Carousel'

const ImageViewer = props => {
    const [index, setIndex] = useState(0);
    const handleSelect = (selectedIndex, e) => setIndex(selectedIndex)

    const images = props.images
    if (!images || images.length === 0) {
        return (<div></div>)
    }

    return (
        <Carousel activeIndex={index} onSelect={handleSelect}>
            <div>
                { images.map((image, index) => {
                    image = "data:image/png;base64, " + image.replace(/(\r\n|\n|\r)/gm, "")
                    return (
                        <div key={index} >
                            <img className="d-block w-100"
                                 src={image}
                                 alt={props.name}/>
                        </div>
                    );
                })}
            </div>
        </Carousel>
    )
};

ImageViewer.propTypes = {
    images: PropTypes.array.isRequired,
    name: PropTypes.string.isRequired
};

export default ImageViewer
