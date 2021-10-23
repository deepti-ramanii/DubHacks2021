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
import "./App.css";
import Map from "./Map";
import { DropdownList } from 'react-widgets'
import "react-widgets/dist/css/react-widgets.css";

interface AppState {
    buildingsList:string[];                         //list of the short names of all buildings on campus
    startBuilding:string;                           //short name of the building to start at
    endBuilding:string;                             //short name of the building to end at
    path:[[number, number], [number, number]][];    //path from startBuilding to endBuilding in the form [[start.X, start.Y][next1.X, next1.Y]]...[[nextN.X, nextN.Y][end.X, end.Y]]
    cost:number;                                    //cost of the path from startBuilding to endBuilding
}

class App extends Component<{}, AppState> {
    constructor(props: any) {
        super(props);
        this.state = {
            buildingsList: [],
            startBuilding: "",
            endBuilding: "",
            path: [],
            cost: 0
        };
        //get the list of all buildings on campus for the dropdown selection
        this.requestBuildings();
    }

    //get a list of all buildings from the server + store in buildingsList
    requestBuildings = async () => {
        try {
            //get the list of buildings as a json string from the server
            let response = await fetch('http://localhost:4567/get-building-names');
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            //we know its an ArrayList of Strings, so convert it and assign it to buildingsList
            const buildingsList = await response.json() as string[];
            this.setState({
                buildingsList: buildingsList
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    //get the shortest path between startBuilding and endBuilding + store in path
    requestPath = async () => {
        try {
            //make sure we have valid input from the dropdowns
            if(this.state.startBuilding == "" || this.state.endBuilding == "") {
                alert("invalid building names");
                return;
            }
            //request path from server using name of buildings to get path between
            let response = await fetch('http://localhost:4567/get-path?start=' + this.state.startBuilding + '&end=' + this.state.endBuilding);
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            //get json string from server, then convert to an array in the form [[start.X, start.Y][next1.X, next1.Y]]...[[nextN.X, nextN.Y][end.X, end.Y]]
            let responsePath = await response.json();
            let path: [[number, number], [number, number]][] = [];
            let cost: number = 0;
            for (let element of responsePath ) {
                let start: [number, number] = [element.start.x, element.start.y];
                let end: [number, number] = [element.end.x, element.end.y];
                path.push([start, end]);
                cost += element.cost;
           }
            //store the path and cost
            this.setState({
                path: path,
                cost: cost
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    //reset all inputs to go back to the initial GUI state (includes the drawn path, the dropdown input, and the cost display
    clear = () => {
        this.setState({
            startBuilding: "",
            endBuilding: "",
            path: [],
            cost: 0
        });
    };

    //render the components on the site
    render() {
        return (
            <div className="App">
                <p>Start building:</p>
                <DropdownList
                    data={this.state.buildingsList}
                    value={this.state.startBuilding}
                    onChange={(value: any) => {
                        this.setState({startBuilding: value})
                    }}
                />
                <p>End building:</p>
                <DropdownList
                    data={this.state.buildingsList}
                    value={this.state.endBuilding}
                    onChange={(value: any) => {
                        this.setState({endBuilding: value})
                    }}
                />
                <button onClick={this.requestPath}>Find Path</button>
                <button onClick={this.clear}>Clear</button>
                <p>Total distance: {this.state.cost} miles</p>
                <Map start={this.state.startBuilding} end={this.state.endBuilding} path={this.state.path}/>
            </div>
        );
    }

}

export default App;
