
import { defineEventHandler, useRuntimeConfig } from '#imports';
import jsonwebtoken from 'jsonwebtoken';

export default defineEventHandler(async (_event) => {
  const config = useRuntimeConfig();
  const tiptapCollabSecret = config.tiptapCollabSecret;

  if (!tiptapCollabSecret) {
    return new Response(
      JSON.stringify({ error: 'No collaboration token provided, please set TIPTAP_COLLAB_SECRET in your environment' }),
      { status: 403 },
    );
  }

  const jwt = await jsonwebtoken.sign(
    {
      /* object to be encoded in the JWT */
    },
    tiptapCollabSecret,
  );

  return new Response(JSON.stringify({ token: jwt }));
});
