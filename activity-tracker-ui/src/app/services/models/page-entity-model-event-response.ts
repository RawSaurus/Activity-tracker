/* tslint:disable */
/* eslint-disable */
import { EntityModelEventResponse } from '../models/entity-model-event-response';
import { PageableObject } from '../models/pageable-object';
import { SortObject } from '../models/sort-object';
export interface PageEntityModelEventResponse {
  content?: Array<EntityModelEventResponse>;
  empty?: boolean;
  first?: boolean;
  last?: boolean;
  number?: number;
  numberOfElements?: number;
  pageable?: PageableObject;
  size?: number;
  sort?: SortObject;
  totalElements?: number;
  totalPages?: number;
}
