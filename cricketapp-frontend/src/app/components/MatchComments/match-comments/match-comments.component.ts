import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatchCommentsDto } from '../../../core/Model/MatchCommentsData/match-comments-dto';
import { MatchCommentsService } from '../../../core/Services/MatchesService/match-comments.service';
import { FormsModule } from '@angular/forms';
import { MatchComments } from '../../../core/Model/MatchCommentsData/match-comments';

@Component({
  selector: 'app-match-comments',
  standalone: true,
  imports: [MatchCommentsComponent, FormsModule , RouterOutlet ,CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './match-comments.component.html',
  styleUrl: './match-comments.component.css'
})
export class MatchCommentsComponent implements OnInit{
  public commentary!: MatchCommentsDto;
  public matchNumber!: number;
  public season!: number;

  constructor(private matchCommentsService: MatchCommentsService, private route: ActivatedRoute) {}

  ngOnInit(): void {
      this.route.params.subscribe(params => {
        this.matchNumber = params["matchNumber"];
        this.season = params["season"];
        this.getCommentary(this.matchNumber, this.season);
      })
  }

  getCommentary(matchNumber: number, season: number) : void {
    this.matchCommentsService.getCommentary(matchNumber, season)
      .subscribe(response => {
        this.commentary = response;
      })
  }

  searchComments(key: string) {
    const result : MatchComments[] = [];
    for(const datas of this.commentary.matchComments[0]) {
      if(
        (datas.outcome.toLowerCase().includes(key.toLowerCase())) || 
        (datas.comment.toLowerCase().includes(key.toLowerCase()))
      ) {
        result.push(datas);
      }
    }

    this.commentary.matchComments[0] = result;    
    
    const result1 : MatchComments[] = [];
    for(const datas of this.commentary.matchComments[1]) {
      if(
        (datas.outcome.toLowerCase().includes(key.toLowerCase())) || 
        (datas.comment.toLowerCase().includes(key.toLowerCase()))
      ) {
        result1.push(datas);
      }
    }

    this.commentary.matchComments[1] = result1;
    if(result.length === 0 && result1.length === 0 || !key)
      this.getCommentary(this.matchNumber, this.season);
    
  }
}
