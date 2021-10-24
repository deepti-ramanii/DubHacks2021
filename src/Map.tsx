/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';
import "./Map.css";

interface MapState {
    backgroundImage: HTMLImageElement | null;
}

interface MapProps {
    start: string;                                  //short name of the start building
    end: string;                                    //short name of the end building
    path: [[number, number], [number, number]][];   //path from startBuilding to endBuilding in the form [[start.X, start.Y][next1.X, next1.Y]]...[[nextN.X, nextN.Y][end.X, end.Y]]
}

class Map extends Component<MapProps, MapState> {
    canvas: React.RefObject<HTMLCanvasElement>;

    constructor(props: MapProps) {
        super(props);
        this.state = {
            backgroundImage: null
        };
        this.canvas = React.createRef();
    }

    componentDidMount() {
        this.fetchAndSaveImage();
        this.redraw();
    }

    componentDidUpdate() {
        this.redraw()
    }

    fetchAndSaveImage() {
        // Creates an Image object, and sets a callback function
        // for when the image is done loading (it might take a while).
        let background: HTMLImageElement = new Image();
        background.onload = () => {
            this.setState({
                backgroundImage: background
            });
        };
        // Once our callback is set up, we tell the image what file it should
        // load from. This also triggers the loading process.
        background.src = "./campus_map.jpg";
    }

    //redraw the path on the canvas every time it updates
    redraw = () => {
        //draw the lines on the map
        let canvas = this.canvas.current;
        if (canvas === null) throw Error("Unable to draw, no canvas ref.");
        let ctx = canvas.getContext("2d");
        if (ctx === null) throw Error("Unable to draw, no valid graphics context.");

        //draw the background image (map)
        if (this.state.backgroundImage !== null) { // This means the image has been loaded.
            // Sets the internal "drawing space" of the canvas to have the correct size.
            // This helps the canvas not be blurry.
            canvas.width = this.state.backgroundImage.width;
            canvas.height = this.state.backgroundImage.height;
            ctx.drawImage(this.state.backgroundImage, 0, 0);
        }

        //draw each segment of the path
        for(let edge of this.props.path) {
            let start: [number, number] = edge[0];
            let end: [number, number] = edge[1];
            ctx.beginPath();
            ctx.strokeStyle = "red";
            ctx.lineWidth = 7;
            ctx.moveTo(start[0], start[1]);
            ctx.lineTo(end[0], end[1]);
            ctx.stroke();
        }
    };

    render() {
        return (
            <canvas ref={this.canvas}/>
        )
    }
}

export default Map;