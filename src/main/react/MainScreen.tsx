import React, {Component} from "react";
import CreateAccountForm from "./CreateAccountForm";
import UpdatePreferencesMatchingForm from "./UpdatePreferencesMatchingForm";
import Game from "./Game";
import UserMatchPolling from "./Components/UserMatchPolling";

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
        if (this.state.is_matched) {
            return;
        }

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
        }
    };

    ChooseDisplay = () => {
        if (!this.state.is_logged_in) {
            return <CreateAccountForm updateUserID={this.updateUserID}/>;
        }
        else if (!this.state.is_matched) {
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
                <UserMatchPolling CheckMatchStatus={this.GetMatched}/>
            </div>
        );
    }
}
export default MainScreen;
