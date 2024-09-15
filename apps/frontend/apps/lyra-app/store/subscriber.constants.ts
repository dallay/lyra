import type {PageResponse} from "@lyra/shared";
import type {
  Subscriber,
  SubscriberCountByStatusResponse,
  SubscriberCountByTagsResponse
} from "~/domain/subscriber";

export const defaultPageResponse: PageResponse<Subscriber> = {
  data: [],
  prevPageCursor: null,
  nextPageCursor: null,
};

export const defaultSubscriberCountByStatus: SubscriberCountByStatusResponse = {
  data: [
    { status: "ENABLED", count: 0 },
    { status: "DISABLED", count: 0 },
    { status: "BLOCKLISTED", count: 0 },
  ],
};

export const defaultSubscriberCountByTag: SubscriberCountByTagsResponse = {
  data: [
    { tag: "bug", count: 0 },
    { tag: "feature", count: 0 },
    { tag: "documentation", count: 0 },
  ],
};
