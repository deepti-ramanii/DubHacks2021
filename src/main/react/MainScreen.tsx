import React, {Component} from "react";
import UserIDInput from "./Components/UserIDInput";
import AgeInput from "./Components/AgeInput";
import PlaystyleInput from "./Components/PlaystyleInput";
import AgePrefInput from "./Components/AgePrefInput";
import PlaystylePrefInput from "./Components/PlaystylePrefInput";
import HobbyInput from "./Components/HobbyInput";
import CreateAccountForm from "./CreateAccountForm";
import UpdatePreferencesMatchingForm from "./UpdatePreferencesMatchingForm";
import {log} from "util";
import Game from "./Game";

interface MainScreenState {
    user_id: string;
    is_logged_in:boolean;
    is_matched:boolean;
}

interface MainScreenProps {

}

/**
 * CreateAccountForm allows user to input string.
 */
class MainScreen extends Component<MainScreenProps, MainScreenState> {
    constructor(props: MainScreenProps) {
        super(props);
        this.state = {
            user_id:'',
            is_logged_in:false,
            is_matched:false
        };
    }

    updateUserID = (id: string, logged_in: boolean):void  => {
        this.setState({
            user_id: id,
            is_logged_in: logged_in
        });
    };

    GetMatched = async () => {
        let response = await fetch('http://localhost:4567/get-is-matched?user_id=' + this.state.user_id);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
        let responsePath = await response.json();
        const successful = JSON.stringify(responsePath);
        if (successful === 'true') {
            this.setState({
                is_matched:true
            });
            alert('Successfully matched!');
        }
    };

    ChooseDisplay = () => {
        if (!this.state.is_logged_in) {
            return <CreateAccountForm updateUserID={this.updateUserID}/>;
        }
        else if (!this.state.is_matched) {
            const response = this.GetMatched();
            alert('called get matched');
            return <UpdatePreferencesMatchingForm user_id={this.state.user_id}/>;
        }
        else {
            return <Game/>
        }
    };

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div>
                <this.ChooseDisplay/>
            </div>
        );
    }
}
export default MainScreen;
