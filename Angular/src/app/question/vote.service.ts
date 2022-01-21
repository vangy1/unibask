import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Entry} from "./entry";
import {AuthenticationService} from "../authentication/authentication.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {map} from "rxjs/operators";

@Injectable()
export class VoteService {

  constructor(private http: HttpClient, private authenticationService: AuthenticationService, private snackBar: MatSnackBar) {
  }

  upvoteEntry(entry: Entry) {
    this.isVotingOnOwnEntry(entry).subscribe(ownEntryVoting => {
      if (ownEntryVoting) {
        this.snackBar.open("Nemôžeš hlasovať za príspevok, ktorý bol pridaný tebou.", undefined, {duration: 3000});
        return
      }

      if (!entry.myVote) { // new vote
        entry.myVote = 1;
        entry.reputation += entry.myVote;
        this.getUpvoteEntryRequest(entry.id).subscribe()
      } else if (entry.myVote < 0) { // from downvote
        entry.reputation -= entry.myVote
        entry.myVote = 1
        entry.reputation += entry.myVote
        this.getUpvoteEntryRequest(entry.id).subscribe()
      } else if (entry.myVote > 0) { // from upvote (same)
        this.unvoteEntry(entry)
      }
    })

  }

  downvoteEntry(entry: Entry) {
    this.isVotingOnOwnEntry(entry).subscribe(ownEntryVoting => {
      if (ownEntryVoting) {
        this.snackBar.open("Nemôžeš hlasovať za príspevok, ktorý bol pridaný tebou.", undefined, {duration: 3000});
        return
      }

      if (!entry.myVote) {
        entry.myVote = -1;
        entry.reputation += entry.myVote;
        this.getDownvoteEntryRequest(entry.id).subscribe()
      } else if (entry.myVote > 0) {
        entry.reputation -= entry.myVote;
        entry.myVote = -1
        entry.reputation += entry.myVote
        this.getDownvoteEntryRequest(entry.id).subscribe()
      } else if (entry.myVote < 0) {
        this.unvoteEntry(entry);
      }
    })
  }

  unvoteEntry(entry: Entry) {

    entry.reputation -= entry.myVote;
    entry.myVote = null;
    this.getUnvoteEntryRequest(entry.id).subscribe();
  }

  isVotingOnOwnEntry(entry: Entry) {
    return this.authenticationService.user.pipe(map(user => {
      if (!entry.author) return false
      return entry.author.mail == user.mail
    }))
  }

  getUpvoteEntryRequest(entryId: number) {
    return this.http.post(environment.apiUrl + '/votes/entry/upvote', {
      entryId: entryId
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true
    })
  }

  getDownvoteEntryRequest(entryId: number) {
    return this.http.post(environment.apiUrl + '/votes/entry/downvote', {
      entryId: entryId
    }, {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true
    })
  }

  getUnvoteEntryRequest(entryId: number) {
    let params = new HttpParams();
    params = params.append('entryId', entryId);

    return this.http.delete(environment.apiUrl + '/votes/entry/unvote', {
      headers: new HttpHeaders({'Content-Type': 'application/json', 'ngsw-bypass': 'true'}),
      withCredentials: true,
      params: params
    })
  }
}
