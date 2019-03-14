<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div class="mb-1"> Add new user</div>
<#if message??>${message}</#if>
<@l.login "/registration" true/>
</@c.page>
