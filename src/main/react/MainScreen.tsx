import React, {Component} from "react";
import UserIDInput from "./Components/UserIDInput";
import AgeInput from "./Components/AgeInput";
import PlaystyleInput from "./Components/PlaystyleInput";
import AgePrefInput from "./Components/AgePrefInput";
import PlaystylePrefInput from "./Components/PlaystylePrefInput";
import HobbyInput from "./Components/HobbyInput";
import CreateAccountForm from "./CreateAccountForm";
import UpdatePreferencesMatchingForm from "./UpdatePreferencesMatchingForm";

interface MainScreenState {
    user_id: string;
    is_logged_in:boolean;
}

/**
 * CreateAccountForm allows user to input string.
 */
class MainScreen extends Component<{}, MainScreenState> {
    constructor(props: any) {
        super(props);
        this.state = {
            user_id: "",
            is_logged_in:false
        };
    }

    ChooseDisplay = () => {
        if (this.state.is_logged_in) {
            return <UpdatePreferencesMatchingForm user_id={this.state.user_id}/>;
        }
        return <CreateAccountForm/>;
    }

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
