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

interface MainScreenState {
    user_id: string;
    is_logged_in:boolean;
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
            is_logged_in:false
        };
    }

    updateUserID = (id: string, logged_in: boolean):void  => {
        this.setState({
            user_id: id,
            is_logged_in: logged_in
        });
    };

    ChooseDisplay = () => {
        if (this.state.is_logged_in) {
            return <UpdatePreferencesMatchingForm user_id={this.state.user_id}/>;
        }
        return <CreateAccountForm updateUserID={this.updateUserID}/>;
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
