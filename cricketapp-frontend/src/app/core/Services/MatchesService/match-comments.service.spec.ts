import { TestBed } from '@angular/core/testing';

import { MatchCommentsService } from './match-comments.service';

describe('MatchCommentsService', () => {
  let service: MatchCommentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchCommentsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
