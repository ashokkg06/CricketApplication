import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchCommentsComponent } from './match-comments.component';

describe('MatchCommentsComponent', () => {
  let component: MatchCommentsComponent;
  let fixture: ComponentFixture<MatchCommentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatchCommentsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MatchCommentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
