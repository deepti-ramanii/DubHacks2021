import React, {Component} from "react";
import UserIDInput from "./UserIDInput";
import AgeInput from "./AgeInput";
import PlaystyleInput from "./PlaystyleInput";
import {Buffer} from "buffer";

/*

Using this component:
https://www.thisdot.co/blog/composing-react-components-with-typescript
look at the "input" section
 */

interface FormState {
    user_id: string;
    player_age: number;
    competitive: boolean;
    uses_vc: boolean;
    likes_anime: boolean;
}

/**
 * Form allows user to input string.
 */
class Form extends Component<{}, FormState> {
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
        var old_competitive = this.state.competitive;
        this.setState({
            competitive: !old_competitive
        });
    };

    setUsesVC = () => {
        var old_usesVC = this.state.uses_vc;
        this.setState({
            uses_vc: !old_usesVC
        });
    }

    setLikesAnime = () => {
        var old_likesAnime = this.state.likes_anime;
        this.setState({
            likes_anime: !old_likesAnime
        });
    };

    AddUser = async () => {
        let response = await fetch('http://localhost:3000/add-user?user_id=' + this.state.user_id +
                                                                          '&age=' + this.state.player_age +
                                                                  '&competitive=' + this.state.competitive +
                                                                      '&uses_vc=' + this.state.uses_vc +
                                                                  '&likes_anime=' +  this.state.likes_anime);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
    }

    UpdatePrefs = async () => {
        let response = await fetch('http://localhost:3000/update-preferences?user_id=' + this.state.user_id +
            '&min_age=' + this.state.player_age +
            '&max_age=' + this.state.player_age +
            '&competitive=' + this.state.competitive +
            '&uses_vc=' + this.state.uses_vc +
            '&use_hobbies=' +  this.state.likes_anime);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
    }

    FindMatch = async () => {
        let response = await fetch('http://localhost:3000/update-preferences?user_id=' + this.state.user_id);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
        let responsePath = await response.json();
        var  playerMatch = JSON.stringify(responsePath);

        // DO SOMETHING WITH THIS
    }

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div id="form">
                <span id="form-label">Add User Info here.</span>
                <UserIDInput user_id={this.state.user_id} setUID={this.setUserId}/>
                <AgeInput player_age={this.state.player_age} setAge={this.setAge}/>
                <PlaystyleInput toggleCompetitive={this.setCompetitive} toggleUsesVC={this.setUsesVC}/>
                <button onClick={this.AddUser}>Submit</button>
            </div>
        );
    }



}

export default Form;
