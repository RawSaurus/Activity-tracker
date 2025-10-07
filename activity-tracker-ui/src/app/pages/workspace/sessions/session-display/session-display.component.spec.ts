import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionDisplayComponent } from './session-display.component';

describe('SessionDisplayComponent', () => {
  let component: SessionDisplayComponent;
  let fixture: ComponentFixture<SessionDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
