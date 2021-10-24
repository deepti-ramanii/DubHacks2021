import React, {Component} from "react";

import UserIDInput from "./Components/UserIDInput";
import AgeInput from "./Components/AgeInput";
import PlaystyleInput from "./Components/PlaystyleInput";
import HobbyInput from "./Components/HobbyInput";

interface CreateAccountFormState {
    user_id: string;
    player_age: number;
    competitive: boolean;
    uses_vc: boolean;
    likes_anime: boolean;
}

/**
 * CreateAccountForm allows user to input string.
 */
class CreateAccountForm extends Component<{}, CreateAccountFormState> {
    constructor(props: any) {
        super(props);
        this.state = {
            user_id: "",
            player_age: 0,
            competitive: false,
            uses_vc: false,
            likes_anime: false
        };
    }

    setUserId = (id: string) => {
        this.setState({
            user_id: id
        });
    };

    setAge = (age: number) => {
        this.setState({
            player_age: age
        });
    };

    setCompetitive = () => {
        const old_competitive = this.state.competitive;
        this.setState({
            competitive: !old_competitive
        });
    };

    setUsesVC = () => {
        const old_usesVC = this.state.uses_vc;
        this.setState({
            uses_vc: !old_usesVC
        });
    };

    setLikesAnime = () => {
        const old_likesAnime = this.state.likes_anime;
        this.setState({
            likes_anime: !old_likesAnime
        });
    };

    AddUser = async () => {
        if (this.state.user_id.length < 5) {
            alert("Invalid username:  must be at least 5 characters long.");
            return;
        }

        let response = await fetch('http://localhost:4567/add-user?user_id=' + this.state.user_id +
                                                                   '&player_age=' + this.state.player_age +
                                                                  '&competitive=' + this.state.competitive +
                                                                      '&uses_vc=' + this.state.uses_vc +
                                                                  '&likes_anime=' + this.state.likes_anime);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
        let responsePath = await response.json();
        const successful = JSON.stringify(responsePath);
        if (successful != 'true') {
            alert('Username already taken. Please try again.');
        }
    };

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div>
                <div id="user_info">
                    <span>Information:</span>
                </div>
                <div id="id_info">
                    <UserIDInput user_id={this.state.user_id} setUID={this.setUserId}/>
                </div>
                <div id="age_info">
                    <AgeInput player_age={this.state.player_age} setAge={this.setAge}/>
                </div>
                <div id="user_prefs">
                    <span>Check the boxes which apply to you: </span>
                </div>
                <div id="playstyle_info">
                    <PlaystyleInput toggleCompetitive={this.setCompetitive} toggleUsesVC={this.setUsesVC}/>
                </div>
                <div id="hobby_info">
                    <HobbyInput toggleLikesAnime={this.setLikesAnime}/>
                </div>
                <div id="create">
                    <button onClick={this.AddUser}>Create Account</button>
                </div>
            </div>
        );
    }
}
export default CreateAccountForm;
