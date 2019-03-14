<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/main" class="form-inline">
            <input type="text" name="filter" class="form-control" value="<#if filter??>${filter}</#if>" placeholder="Search by tag"/>
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>
<p>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group ">
                <input class="form-control ${(textError??)?string('is-invalid', '')}"
                       type="text" name="text"
                       value="<#if message??>${message.text}</#if>" placeholder="Введите сообщение" />
                <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
                </#if>
            </div>
            <div class="form-group ">
                <input class="form-control" type="text" name="tag"
                       value="<#if message??>${message.tag}</#if>" placeholder="Тэг"/>
                <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
                </#if>
            </div>
            <div class="form-group ">
                <input  type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>
            <div class="form-group ">
                <div class="custom-file">
                    <input class="form-control" id="customFile" type="file" name="file"/>
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <div class="form-group ">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>
</div>
<div class="card-columns">
    <#list messages as message>

        <div class="card my-3">
            <div>
        <#if message.filename??>
            <img class="card-img-top" src="/img/${message.filename}">
        </#if>
            </div>
            <div class="m-2">
                <i>${message.text}</i>
                <span>${message.tag}</span>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>

    <#else>
No message
    </#list>
</div>
</@c.page>
