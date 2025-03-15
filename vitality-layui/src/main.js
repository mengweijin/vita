import "layui/dist/css/layui.css";
import "layui";

import "@/styles/index.css";
import { ajaxSetup } from "@/scripts/ajaxSetup.js";
import { admin } from "@/scripts/admin.js";

layui.use(function () {
  let $ = layui.jquery;
  let layer = layui.layer;
  ajaxSetup($, layer);

  admin.render();
});
