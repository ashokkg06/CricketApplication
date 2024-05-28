import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MatchSquadComponent } from './match-squad.component';

describe('MatchSquadComponent', () => {
  let component: MatchSquadComponent;
  let fixture: ComponentFixture<MatchSquadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MatchSquadComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MatchSquadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
