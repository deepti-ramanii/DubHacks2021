import React, {Component} from 'react';
import "./App.css";
import TextInput from "./components/TextInput";
// import "react-widgets/dist/css/react-widgets.css";

interface AppState {
    uid:string;
    preferences:string;
}

class App extends Component<{}, AppState> {
    constructor(props: any) {
        super(props);
        this.state = {
            uid: "",
            preferences: "",
        };

        //this.requestID();
        //this.requestPreferences(); // used to be requestBuildings
    }

    componentDidMount() {
        // Do any one-time setup here, when the app has just loaded
    }

    // get user name
    requestID = async () => {
        try {
            //get the list of buildings as a json string from the server
            let response = await fetch('http://localhost:4567/get-id');
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            //we know its an ArrayList of Strings, so convert it and assign it to buildingsList
            const uid = await response.json() as string;
            this.setState({
                uid: uid,
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    // get preferences
    requestPreferences = async () => {
        try {
            //get the list of buildings as a json string from the server
            let response = await fetch('http://localhost:4567/get-preferences');
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }
            //we know its an ArrayList of Strings, so convert it and assign it to buildingsList
            const preferences = await response.json() as string;
            this.setState({
                preferences: preferences
            });
        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    //get the shortest path between startBuilding and endBuilding + store in path
    /*
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
    }; */

    //reset all inputs to go back to the initial GUI state (includes the drawn path, the dropdown input, and the cost display
    clear = () => {
        this.setState({
            preferences: "",
            uid: "",
        });
    };

    // Updates uid; sets state
    updateUserId: (s: string) => void = (inputUid: string) => {
        this.setState({
            uid: inputUid,
        });
    }

    //render the components on the site
    render() {
        return (
            <div id="App">
                <p id="app-title">App Title</p>
                <TextInput
                    inputValue={"unset id *replace w/ empty string*"}
                    onSubmit={this.updateUserId}
                />
            </div>
        );
    }

}

export default App;